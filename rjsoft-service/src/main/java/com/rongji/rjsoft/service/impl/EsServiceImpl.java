package com.rongji.rjsoft.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rongji.rjsoft.ao.search.DocAo;
import com.rongji.rjsoft.ao.search.DocDeleteAo;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.query.search.SearchBaseQuery;
import com.rongji.rjsoft.query.search.SearchMultiPageQuery;
import com.rongji.rjsoft.query.search.SearchPageQuery;
import com.rongji.rjsoft.query.search.SearchQuery;
import com.rongji.rjsoft.service.IEsService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author JohnYehyo
 * @date 2019-12-4
 */
@Service
public class EsServiceImpl implements IEsService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName, String settings) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        /**
         * {
         * 	"settings":{
         * 		"index":{
         * 			"number_of_shards":1,
         * 			"number_of_replicas":0
         *       }
         *    }
         * }
         */
        request.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
        request.mapping(settings, XContentType.JSON);
        //通过ES连接对象获取索引库管理对象
        IndicesClient indicesClient = restHighLevelClient.indices();
        CreateIndexResponse response = indicesClient.create(request);
        //返回的CreateIndexResponse允许检索有关执行的操作的信息：
        //指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        //指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @throws IOException
     */
    @Override
    public void deleteIndex(String indexName) throws IOException {
        //指定要删除的索引名称
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        //可选参数：
        //设置超时，等待所有节点确认索引删除（使用TimeValue形式）
        request.timeout(TimeValue.timeValueMinutes(2));
        //设置超时，等待所有节点确认索引删除（使用字符串形式）
        // request.timeout("2m");
        //连接master节点的超时时间(使用TimeValue方式)
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        //连接master节点的超时时间(使用字符串方式)
        // request.masterNodeTimeout("1m");

        //设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        //同步执行
        IndicesClient indices = restHighLevelClient.indices();
        AcknowledgedResponse deleteRsponse = indices.delete(request, RequestOptions.DEFAULT);

        //返回的DeleteIndexResponse允许检索有关执行的操作的信息，如下所示：
        //是否所有节点都已确认请求
        boolean acknowledged = deleteRsponse.isAcknowledged();
        System.out.println(acknowledged);

        //如果找不到索引，则会抛出ElasticsearchException：
        try {
            request = new DeleteIndexRequest("does_not_exist");
            restHighLevelClient.indices().delete(request);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                //如果没有找到要删除的索引，要执行某些操作
                LogUtils.error("没有找到要删除的索引,index:" + indexName);
            }
        }
    }

    /**
     * 增加/更新文档
     *
     * @param docAo 文档参数
     */
    @Override
    public void addDoc(DocAo docAo) throws IOException {
        IndexRequest indexRequest = new IndexRequest(docAo.getIndex());
        if (StringUtils.isNotEmpty(docAo.getType())) {
            indexRequest.type(docAo.getType());
        } else {
            indexRequest.type("_doc");
        }
        if (null != docAo.getId()) {
            indexRequest.id(String.valueOf(docAo.getId()));
        }
        indexRequest.source(docAo.getContent(), XContentType.JSON);
        //同步
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                LogUtils.error("增加/更新数据异常:" + reason);
            }
            throw new BusinessException(ResponseEnum.DOC_INSERT_EDIT_ERROR);
        }
    }

    /**
     * 根据id删除文档
     *
     * @param docDeleteAo 文档参数
     */
    @Override
    public void deleteDoc(DocDeleteAo docDeleteAo) throws IOException {
        DeleteRequest request = new DeleteRequest(docDeleteAo.getIndex());
        if (StringUtils.isNotEmpty(docDeleteAo.getType())) {
            request.type(docDeleteAo.getType());
        }
        if (null != docDeleteAo.getId()) {
            request.id(String.valueOf(docDeleteAo.getId()));
        }
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                LogUtils.error("删除数据异常:" + reason);
            }
            throw new BusinessException(ResponseEnum.DOC_DELETE_ERROR);
        }
    }

    /**
     * 通过id获取文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public GetResponse findDocById(String indexName, String type, String id) throws IOException {
        GetRequest request = new GetRequest(indexName, type, id);
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response;
    }

    /**
     * 综合查询
     *
     * @param searchPageQuery 查询条件
     * @param <T>             泛型
     * @return 翻页结果
     */
    @Override
    public <T> ResponseVo<T> queryForlist(SearchPageQuery searchPageQuery) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(searchPageQuery.getIndexName());
        searchRequest.types(searchPageQuery.getIndexType());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(searchPageQuery.getCurrent() - 1);
        sourceBuilder.size(searchPageQuery.getPageSize());
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        RangeQueryBuilder rangeQueryBuilder = rangeQueryByTime(searchPageQuery.getSTime(),
                searchPageQuery.getETime(), searchPageQuery.getTimeKey());
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (CollectionUtil.isEmpty(searchPageQuery.getParam())) {
            //条件和值都为空只时间范围查询然后合并时间范围查询和pid前缀查询
            //orgid不为空合并pid的前缀查询
            if (!StringUtils.isEmpty(searchPageQuery.getBranchCode())) {
                MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
                        new MatchPhrasePrefixQueryBuilder(searchPageQuery.getBranchKey(), searchPageQuery.getBranchCode());
                boolBuilder.must(matchPhrasePrefixQueryBuilder);
            }

            //时间范围查询不为空合并
            if (null != rangeQueryBuilder) {
                boolBuilder.must(rangeQueryBuilder);
            }
            sourceBuilder.query(boolBuilder);
        } else {
            //条件合并布尔查询
            //pid不为空合并pid的的前缀查询
            if (!StringUtils.isEmpty(searchPageQuery.getBranchCode())) {
                MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
                        new MatchPhrasePrefixQueryBuilder(searchPageQuery.getBranchKey(), searchPageQuery.getBranchCode());
                boolBuilder.must(matchPhrasePrefixQueryBuilder);
            }
            //时间查询不为空合并
            if (null != rangeQueryBuilder) {
                boolBuilder.must(rangeQueryBuilder);

            }

            List<SearchBaseQuery> params = searchPageQuery.getParam();
            addBoolQuery(sourceBuilder, boolBuilder, params, false);

        }
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//        sourceBuilder.sort(new FieldSortBuilder("executionTime.keyword").order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LogUtils.error("ES查询失败:", e);
            return ResponseVo.error("查询失败");
        }
        SearchHit[] results = searchResponse.getHits().getHits();
        long totalHits = searchResponse.getHits().getTotalHits();
        if (null == results || results.length == 0) {
            return null;
        }

        CommonPage page = new CommonPage();
        if (null == searchPageQuery.getClazz()) {
            List list = getOriginals(results);
            page.setList(list);
        } else {
            List<T> list = getOriginals(searchPageQuery.getClazz(), results);
            page.setList(list);
        }
        page.setCurrent((long) searchPageQuery.getCurrent());
        page.setPageSize((long) searchPageQuery.getPageSize());
        page.setTotalPage(totalHits % searchPageQuery.getPageSize() == 0 ?
                totalHits / searchPageQuery.getPageSize() : totalHits / searchPageQuery.getPageSize() + 1);
        page.setTotal(totalHits);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);

    }

    private List getOriginals(SearchHit[] results) {
        List list = new ArrayList<>();
        Object obj;
        for (SearchHit hit : results) {
            String sourceAsString = hit.getSourceAsString();
            if (sourceAsString != null) {
                obj = JSON.parseObject(sourceAsString);
                list.add(obj);
            }
        }
        return list;
    }

    private <T> List<T> getOriginals(Class<T> clazz, SearchHit[] results) {
        T t;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        List<T> list = new ArrayList<>();
        for (SearchHit hit : results) {
            String sourceAsString = hit.getSourceAsString();
            if (sourceAsString != null) {
                t = JSON.parseObject(sourceAsString, clazz);
                if (null == t) {
                    continue;
                }
                list.add(t);
            }
        }
        return list;
    }

    private RangeQueryBuilder rangeQueryByTime(Long sTime, Long eTime, String key) {
        if (null != sTime && null != eTime) {
            RangeQueryBuilder rangeQueryTime = QueryBuilders.rangeQuery(key).from(sTime)
                    .to(eTime);
            return rangeQueryTime;
        }
        if (null != sTime) {
            RangeQueryBuilder rangeQueryTime = QueryBuilders.rangeQuery(key).gte(sTime);
            return rangeQueryTime;
        }
        if (null != eTime) {
            RangeQueryBuilder rangeQueryTime = QueryBuilders.rangeQuery(key).lte(eTime);
            return rangeQueryTime;
        }
        return null;
    }


    /**
     * 单实例查询
     *
     * @param searchQuery 查询条件
     * @param <T>         泛型
     * @return 实例结果
     */
    @Override
    public <T> ResponseVo<T> queryForEntity(SearchQuery searchQuery) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(searchQuery.getIndexName());
        searchRequest.types(searchQuery.getIndexType());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        List<SearchBaseQuery> params = searchQuery.getParam();
        addBoolQuery(sourceBuilder, boolBuilder, params, false);


        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LogUtils.error("ES查询失败:", e);
            return ResponseVo.error("查询失败");
        }
        SearchHit[] results = searchResponse.getHits().getHits();
        if (null == results || results.length == 0) {
            return null;
        }
        if (null == searchQuery.getClazz()) {
            List list = getOriginals(results);
            if (null == list || list.size() == 0) {
                return null;
            }
            return ResponseVo.response(ResponseEnum.SUCCESS, list.get(0));
        }
        List<T> list = getOriginals(searchQuery.getClazz(), results);
        if (null == list || list.size() == 0) {
            return null;
        }
        return ResponseVo.response(ResponseEnum.SUCCESS, list.get(0));

    }

    private void addBoolQuery(SearchSourceBuilder sourceBuilder, BoolQueryBuilder boolBuilder,
                              List<SearchBaseQuery> params, boolean fuzzy) {
        for (SearchBaseQuery param : params) {
            //条件和值都不为空进行模糊查询然后合并时间范围查询和pid前缀查询
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(param.getKey(),
                    param.getValue());
            if (fuzzy) {
                // 启动模糊查询
                matchQueryBuilder.fuzziness(Fuzziness.AUTO);
            }
            //合并模糊查询
            boolBuilder.must(matchQueryBuilder);
        }
        sourceBuilder.query(boolBuilder);
    }

    /**
     * 多字段包含关键字搜索
     *
     * @param searchMultiPageQuery 条件对象
     * @return 分页结果
     */
    @Override
    public <T> ResponseVo<T> multiSelect(SearchMultiPageQuery searchMultiPageQuery) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(searchMultiPageQuery.getIndexName());
        searchRequest.types(searchMultiPageQuery.getIndexType());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(searchMultiPageQuery.getCurrent() - 1);
        sourceBuilder.size(searchMultiPageQuery.getPageSize());
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        if (null == searchMultiPageQuery.getParam()) {
            throw new BusinessException(ResponseEnum.NONE_SELECT_PARAM);
        }
        MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(
                searchMultiPageQuery.getParam().getValue(), searchMultiPageQuery.getParam().getKey()
        );
        multiMatchQueryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        multiMatchQueryBuilder.fuzziness(Fuzziness.AUTO);
        sourceBuilder.query(multiMatchQueryBuilder);

        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LogUtils.error("ES查询失败:", e);
            return ResponseVo.error("查询失败");
        }
        SearchHit[] results = searchResponse.getHits().getHits();
        long totalHits = searchResponse.getHits().getTotalHits();
        if (null == results || results.length == 0) {
            return null;
        }

        CommonPage page = new CommonPage();
        if (null == searchMultiPageQuery.getClazz()) {
            List list = getOriginals(results);
            page.setList(list);
        } else {
            List<T> list = getOriginals(searchMultiPageQuery.getClazz(), results);
            page.setList(list);
        }
        page.setCurrent((long) searchMultiPageQuery.getCurrent());
        page.setPageSize((long) searchMultiPageQuery.getPageSize());
        page.setTotalPage(totalHits % searchMultiPageQuery.getPageSize() == 0 ?
                totalHits / searchMultiPageQuery.getPageSize() : totalHits / searchMultiPageQuery.getPageSize() + 1);
        page.setTotal(totalHits);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }
}
