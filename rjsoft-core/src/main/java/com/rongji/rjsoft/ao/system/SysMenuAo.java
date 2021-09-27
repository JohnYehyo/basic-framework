package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 菜单参数
 * @author: JohnYehyo
 * @create: 2021-08-30 14:04:55
 */
@Data
public class SysMenuAo {

    public interface update {
    }

    public interface add {
    }

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id", required = false)
    @NotNull(
            groups = {SysMenuAo.update.class},
            message = "菜单id不能为空"
    )
    private Long menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotEmpty(groups = {SysMenuAo.add.class, SysMenuAo.update.class}, message = "菜单名称不能为空")
    private String menuName;

    /**
     * 父菜单名称
     */
    @ApiModelProperty(value = "父菜单名称", required = false)
    private String parentName;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单id", required = false)
    private Long parentId;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", required = true)
    @NotEmpty(groups = {SysMenuAo.add.class, SysMenuAo.update.class}, message = "显示顺序不能为空")
    private String orderNum;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", required = true)
    @NotEmpty(groups = {SysMenuAo.add.class, SysMenuAo.update.class}, message = "路由地址不能为空")
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", required = false)
    private String component;

    /**
     * 是否为外链（0是 1否）
     */
    @ApiModelProperty(value = "是否为外链", required = false)
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @ApiModelProperty(value = "是否缓存", required = false)
    private String isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "菜单类型", required = true)
    @NotEmpty(groups = {SysMenuAo.add.class, SysMenuAo.update.class}, message = "菜单类型不能为空")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "显示状态", required = false)
    private String visible;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "菜单状态", required = false)
    private String status;

    /**
     * 权限字符串
     */
    @ApiModelProperty(value = "权限字符串", required = false)
    private String perms;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", required = false)
    private String icon;

}
