package com.rongji.rjsoft.vo.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 文章类别树视图
 * @author: JohnYehyo
 * @create: 2021-09-16 14:06:07
 */
@Data
public class CmsCategoryTreeVo implements Serializable {

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
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点")
    private boolean parentNode;
}
