package com.rongji.rjsoft.vo.content;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 栏目视图
 * @author: JohnYehyo
 * @create: 2021-09-15 18:55:00
 */
@Data
public class CmsColumnVo implements Serializable {

    private static final long serialVersionUID = 2953467267863632596L;

    /**
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目id")
    private Long columnId;

    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称")
    private String columnName;

    /**
     * 父级栏目id
     */
    @ApiModelProperty(value = "父级栏目id")
    private Long parentId;

}
