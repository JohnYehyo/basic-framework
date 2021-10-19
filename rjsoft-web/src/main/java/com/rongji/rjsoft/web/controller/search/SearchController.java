package com.rongji.rjsoft.web.controller.search;

import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.query.search.SearchPageQuery;
import com.rongji.rjsoft.query.search.SearchQuery;
import com.rongji.rjsoft.service.IEsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description: 搜索工具
 * @author: JohnYehyo
 * @create: 2021-10-15 12:51:55
 */
@Api(tags = "搜索工具")
@RestController
@RequestMapping(value = "search")
@AllArgsConstructor
public class SearchController {

    private final IEsService esService;

    /**
     * 多条件搜索
     * @param searchPageQuery 条件对象
     * @return 分页结果
     */
    @ApiOperation(value = "多条件搜索")
    @GetMapping(value = "list")
    public Object list(@Valid SearchPageQuery searchPageQuery) {
        searchPageQuery.setIndexName("basic_log_index");
        searchPageQuery.setIndexType("doc");
        searchPageQuery.setClazz(SysOperationLog.class);
        return esService.queryForlist(searchPageQuery);
    }

    /**
     * 详情
     * @param searchQuery 条件对象
     * @return 分页结果
     */
    @ApiOperation(value = "搜索详情")
    @GetMapping(value = "info")
    public Object list(@Valid SearchQuery searchQuery) {
        return esService.queryForEntity(searchQuery);
    }
}
