package com.rongji.rjsoft.service;

import com.rongji.rjsoft.ao.search.DocAo;
import com.rongji.rjsoft.ao.search.DocDeleteAo;
import com.rongji.rjsoft.query.search.SearchPageQuery;
import com.rongji.rjsoft.query.search.SearchQuery;
import com.rongji.rjsoft.vo.ResponseVo;
import org.elasticsearch.action.get.GetResponse;

import java.io.IOException;

/**
 * @author JohnYehyo
 * @date 2019-12-4
 */
public interface IEsService {


    /**
     * 创建索引
     *
     * @param indexName
     * @param settings
     * @throws IOException
     */
    void createIndex(String indexName, String settings) throws IOException;

    /**
     * 删除索引
     *
     * @param indexName
     * @throws IOException
     */
    void deleteIndex(String indexName) throws IOException;

    /**
     * 增加/更新文档
     * @param docAo 文档参数
     */
    void addDoc(DocAo docAo) throws IOException;

    /**
     * 根据id删除文档
     * @param docDeleteAo 文档参数
     */
    void deleteDoc(DocDeleteAo docDeleteAo) throws IOException;

    /**
     * 通过id获取文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    GetResponse findDocById(String indexName, String type, String id) throws IOException;

    /**
     * 综合查询
     *
     * @param searchPageQuery 查询条件
     * @param <T>             泛型
     * @return 分页结果
     * @throws IOException
     */
    <T> ResponseVo<T> queryForlist(SearchPageQuery searchPageQuery);


    /**
     * 单实例查询
     *
     * @param searchQuery 查询条件
     * @param <T>         泛型
     * @return 实例结果
     * @throws IOException
     */
    <T> ResponseVo<T> queryForEntity(SearchQuery searchQuery);

}
