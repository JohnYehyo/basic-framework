package com.rongji.rjsoft.query.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 分页查询
 * @author: JohnYehyo
 * @create: 2021-09-01 13:24:49
 */
@Data
public class PageQuery {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer current = 1;

    /**
     * 每页条目
     */
    @ApiModelProperty(value = "每页条目")
    private Integer pageSize = 10;
}
