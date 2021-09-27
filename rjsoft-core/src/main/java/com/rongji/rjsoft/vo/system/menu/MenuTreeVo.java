package com.rongji.rjsoft.vo.system.menu;

import lombok.Data;

import java.util.List;

/**
 * @description: 树菜单
 * @author: JohnYehyo
 * @create: 2021-09-01 19:45:19
 */
@Data
public class MenuTreeVo {

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 子节点
     */
    private List<MenuTreeVo> children;
}
