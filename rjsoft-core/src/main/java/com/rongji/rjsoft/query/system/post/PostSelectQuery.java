package com.rongji.rjsoft.query.system.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 岗位下拉查询
 * @author: JohnYehyo
 * @create: 2021-08-30 14:17:50
 */
@Data
public class PostSelectQuery {

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称", dataType = "String")
    private String postName;

}
