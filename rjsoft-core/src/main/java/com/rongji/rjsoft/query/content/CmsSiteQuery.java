package com.rongji.rjsoft.query.content;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 站点查询对象
 * @author: JohnYehyo
 * @create: 2021-09-15 18:25:16
 */
@Data
public class CmsSiteQuery extends PageQuery {

    /**
     * 站点ID
     */
    @ApiModelProperty(value = "站点ID")
    private Long siteId;

    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称")
    private String siteName;
}
