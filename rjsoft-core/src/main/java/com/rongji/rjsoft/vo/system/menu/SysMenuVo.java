package com.rongji.rjsoft.vo.system.menu;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 菜单视图
 * @author: JohnYehyo
 * @create: 2021-08-30 15:55:51
 */
@Data
public class SysMenuVo implements Serializable {

    private static final long serialVersionUID = 6531287641447281117L;
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链（0是 1否）
     */
    private int isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private int isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private int visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private int status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
