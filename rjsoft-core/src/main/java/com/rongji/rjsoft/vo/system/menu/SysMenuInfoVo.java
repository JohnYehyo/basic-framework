package com.rongji.rjsoft.vo.system.menu;

import com.rongji.rjsoft.entity.system.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @description: 菜单
 * @author: JohnYehyo
 * @create: 2021-08-31 17:03:46
 */
@Data
public class SysMenuInfoVo extends SysMenu {

    /**
     * 子菜单
     */
    private List<SysMenuInfoVo> children;
}
