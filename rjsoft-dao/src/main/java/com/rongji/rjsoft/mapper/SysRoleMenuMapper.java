package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.entity.system.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量保存角色菜单信息
     * @param list 菜单信息
     * @return
     */
    int batchSaveRoleMenu(List<SysRoleMenu> list);
}
