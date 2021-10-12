package com.rongji.rjsoft;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.query.search.SearchBaseQuery;
import com.rongji.rjsoft.query.search.SearchPageQuery;
import com.rongji.rjsoft.query.search.SearchQuery;
import com.rongji.rjsoft.service.IEsService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.web.Applicaiton;
import org.junit.Assert;
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
    public void List() throws IOException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sTime = format.parse("2020-03-01 00:00:00");
        Date eTime = format.parse("20201-10-12 00:00:00");;
        Long begin = sTime == null ? null : sTime.getTime();
        Long end = eTime == null ? null : eTime.getTime();

        SearchPageQuery<SysOperationLog> searchPageQuery = new SearchPageQuery<>();
        searchPageQuery.setIndexName("basic_log_index");
        searchPageQuery.setIndexType("doc");

        List<SearchBaseQuery> params = new ArrayList<>();
        SearchBaseQuery searchBaseQuery = new SearchBaseQuery();
        searchBaseQuery.setKey("ip");
        searchBaseQuery.setValue("192.168.4.249");
        params.add(searchBaseQuery);
        SearchBaseQuery searchBaseQuery1 = new SearchBaseQuery();
        searchBaseQuery1.setKey("method");
        searchBaseQuery1.setValue("修改个人信息");
        params.add(searchBaseQuery1);

        searchPageQuery.setParam(params);
        searchPageQuery.setTimeKey("time");
        searchPageQuery.setSTime(begin);
        searchPageQuery.setETime(end);
        searchPageQuery.setBranchKey("branchKey");
        searchPageQuery.setBranchCode("");
        searchPageQuery.setClazz(SysOperationLog.class);
        CommonPage<SysOperationLog> page = esService.queryForlist(searchPageQuery);
        System.out.println(JSON.toJSONString(page));
    }

    @Test
    public void entity() throws IOException, ParseException {
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
        SysOperationLog sysOperationLog = esService.queryForEntity(searchQuery);
        System.out.println(sysOperationLog);
    }

    @Test
    public void deleteIndex() throws IOException {
        esService.deleteIndex("basic_log_index");
    }

}
