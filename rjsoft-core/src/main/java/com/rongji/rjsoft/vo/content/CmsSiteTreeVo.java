package com.rongji.rjsoft.vo.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 站点树
 * @author: JohnYehyo
 * @create: 2021-09-15 19:30:49
 */
@Data
public class CmsSiteTreeVo implements Serializable {

    /**
     * 站点ID
     */
    @ApiModelProperty(value = "站点id")
    private Long siteId;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称")
    private String siteName;

    /**
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点")
    private boolean parentNode;
}
