package com.rongji.rjsoft.vo.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 栏目树视图
 * @author: JohnYehyo
 * @create: 2021-09-15 18:55:00
 */
@Data
public class CmsColumnTreeVo implements Serializable {

    private static final long serialVersionUID = 2953467267863632596L;

    /**
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目id")
    private Long columnId;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称")
    private String columnName;

    /**
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点")
    private boolean parentNode;

}
