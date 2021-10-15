package com.rongji.rjsoft.web.controller.search;

import com.rongji.rjsoft.query.search.SearchPageQuery;
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

    @ApiOperation(value = "多条件搜索")
    @GetMapping(value = "list")
    public Object list(@Valid SearchPageQuery searchPageQuery) {
        return esService.queryForlist(searchPageQuery);
    }
}
