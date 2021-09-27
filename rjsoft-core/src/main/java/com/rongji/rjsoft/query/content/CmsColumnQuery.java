package com.rongji.rjsoft.query.content;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 栏目查询对象
 * @author: JohnYehyo
 * @create: 2021-09-15 18:25:16
 */
@Data
public class CmsColumnQuery extends PageQuery {

    /**
     * 站点ID
     */
    @ApiModelProperty(value = "栏目ID")
    private Long columnId;

    /**
     * 站点名
     */
    @ApiModelProperty(value = "栏目名")
    private String columnName;

}
