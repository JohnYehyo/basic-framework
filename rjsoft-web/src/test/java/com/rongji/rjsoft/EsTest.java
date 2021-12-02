package com.rongji.rjsoft;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.query.search.*;
import com.rongji.rjsoft.service.IEsService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.web.Applicaiton;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 搜索引擎测试
 * @author: JohnYehyo
 * @create: 2021-10-09 16:09:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Applicaiton.class})
public class EsTest {

    @Autowired
    private IEsService esService;

    @Test
    public void List() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sTime = format.parse("2020-03-01 00:00:00");
        Date eTime = format.parse("2021-12-12 00:00:00");
        ;
        Long begin = sTime == null ? null : sTime.getTime();
        Long end = eTime == null ? null : eTime.getTime();

        SearchPageQuery<SysOperationLog> searchPageQuery = new SearchPageQuery<>();
        searchPageQuery.setIndexName("basic_log_index");
        searchPageQuery.setIndexType("doc");

        List<SearchBaseQuery> params = new ArrayList<>();
        SearchBaseQuery searchBaseQuery = new SearchBaseQuery();
        searchBaseQuery.setKey("username");
        searchBaseQuery.setValue("河南");
        params.add(searchBaseQuery);
        SearchBaseQuery searchBaseQuery1 = new SearchBaseQuery();
        searchBaseQuery1.setKey("username");
        searchBaseQuery1.setValue("司法厅");
        params.add(searchBaseQuery1);

        searchPageQuery.setParam(params);
        searchPageQuery.setTimeKey("time");
        searchPageQuery.setSTime(begin);
        searchPageQuery.setETime(end);
        searchPageQuery.setBranchKey("branchKey");
        searchPageQuery.setBranchCode("");
        searchPageQuery.setClazz(SysOperationLog.class);
        CommonPage<SysOperationLog> obj = (CommonPage<SysOperationLog>) esService.queryForlist(searchPageQuery).getData();
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void entity() {
        SearchQuery<SysOperationLog> searchQuery = new SearchQuery<>();
        searchQuery.setIndexName("basic_log_index");
        searchQuery.setIndexType("doc");
        List<SearchBaseQuery> params = new ArrayList<>();
        SearchBaseQuery searchBaseQuery = new SearchBaseQuery();
        searchBaseQuery.setKey("id");
        searchBaseQuery.setValue("1");
        params.add(searchBaseQuery);
        searchQuery.setParam(params);
        searchQuery.setClazz(SysOperationLog.class);
        SysOperationLog sysOperationLog = (SysOperationLog) esService.queryForEntity(searchQuery).getData();
        System.out.println(sysOperationLog);
    }

    @Test
    public void deleteIndex() throws IOException {
        esService.deleteIndex("basic_log_index");
    }

    @Test
    public void multiSelect() {

        SearchMultiPageQuery searchMultiPageQuery = new SearchMultiPageQuery();
        searchMultiPageQuery.setIndexName("index_log");
        searchMultiPageQuery.setIndexType("_doc");

        String[] keys = {"username", "password"};
        String value = "河南";

        SearchMultiBaseQuery searchMultiBaseQuery = new SearchMultiBaseQuery();
        searchMultiBaseQuery.setKey(keys);
        searchMultiBaseQuery.setValue(value);
        searchMultiPageQuery.setParam(searchMultiBaseQuery);

        Object obj = esService.multiSelect(searchMultiPageQuery);
        System.out.println(JSON.toJSONString(obj));
    }

}
