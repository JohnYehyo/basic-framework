package com.rongji.rjsoft.vo.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * @description: 文章类别视图
 * @author: JohnYehyo
 * @create: 2021-09-16 14:06:07
 */
@Data
public class CmsCategoryVo implements Serializable {

    private static final long serialVersionUID = -136941801905312782L;

    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id")
    private Long categoryId;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 类型名
     */
    @ApiModelProperty(value = "类型名")
    private String categoryName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用")
    private int status;
}
