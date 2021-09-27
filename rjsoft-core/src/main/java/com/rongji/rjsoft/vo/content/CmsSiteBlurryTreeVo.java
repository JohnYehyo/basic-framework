package com.rongji.rjsoft.vo.content;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 模糊查询树
 * @author: JohnYehyo
 * @create: 2021-09-16 09:23:54
 */
@Data
public class CmsSiteBlurryTreeVo extends CmsSiteTreeVo implements Serializable {

    private static final long serialVersionUID = 6368658678352593375L;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<CmsSiteBlurryTreeVo> children;

    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点id")
    private Long parentId;
}
