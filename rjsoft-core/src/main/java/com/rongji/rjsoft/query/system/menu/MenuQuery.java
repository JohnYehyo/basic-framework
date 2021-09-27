package com.rongji.rjsoft.query.system.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 菜单查询
 * @author: JohnYehyo
 * @create: 2021-08-30 14:17:09
 */
@Data
public class MenuQuery {

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态")
    private int status;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

}
