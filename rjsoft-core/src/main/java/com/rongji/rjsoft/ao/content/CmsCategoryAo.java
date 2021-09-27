package com.rongji.rjsoft.ao.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 文章类别表单
 * @author: JohnYehyo
 * @create: 2021-09-15 18:07:11
 */
@Data
public class CmsCategoryAo {

    public interface insert {

    }

    public interface update {

    }

    /**
     * 文章类别ID
     */
    @ApiModelProperty(value = "文章类别ID")
    @NotNull(
            groups = {CmsCategoryAo.update.class},
            message = "文章类别ID不能为空"
    )
    private Long categoryId;

    /**
     * 文章类别名称
     */
    @ApiModelProperty(value = "文章类别名称", required = true)
    @NotEmpty(message = "文章类别名称不能为空")
    private String categoryName;

    /**
     * 文章类别父级id
     */
    @ApiModelProperty(value = "父级ID", required = true)
    @NotNull(message = "父级ID不能为空")
    private Long parentId;

    /**
     * 文章类别描述
     */
    @ApiModelProperty(value = "文章类别描述")
    private String description;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNum;


    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用")
    private Integer status;

}
