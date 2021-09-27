package com.rongji.rjsoft.query.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 站点查询对象
 * @author: JohnYehyo
 * @create: 2021-09-18 09:29:37
 */
@Data
public class CmsTagsSimpleQuery{

    /**
     * 站点名
     */
    @ApiModelProperty(value = "站点名")
    private String tagName;
}
