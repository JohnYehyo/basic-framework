package com.rongji.rjsoft.query.content;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 站点查询对象
 * @author: JohnYehyo
 * @create: 2021-09-18 09:29:37
 */
@Data
public class CmsTagsQuery extends PageQuery {

    /**
     * 站点名
     */
    @ApiModelProperty(value = "站点名")
    private String tagName;
}
