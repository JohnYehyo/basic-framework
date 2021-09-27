package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.system.SysMenuAo;
import com.rongji.rjsoft.entity.system.SysMenu;
import com.rongji.rjsoft.query.system.menu.MenuQuery;
import com.rongji.rjsoft.vo.system.menu.SysMenuInfoVo;
import com.rongji.rjsoft.vo.system.menu.SysMenuVo;
import com.rongji.rjsoft.vo.system.menu.MenuTreeVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 通过菜单名检查菜单是否存在
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    boolean checkMenuByName(SysMenuAo sysMenuAo);

    /**
     * 添加菜单
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    int saveMenu(SysMenuAo sysMenuAo);

    /**
     * 编辑菜单
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    int editMenu(SysMenuAo sysMenuAo);

    /**
     * 删除菜单
     * @param menuIds 菜单id
     * @return 返回结果
     */
    int deleteMenu(Long[] menuIds);

    /**
     * 通过用户菜单列表查询
     * @param menuQuery 查询参数
     * @return 返回结果
     */
    List<SysMenuInfoVo> listOfMenu(MenuQuery menuQuery);

    /**
     * 通过id获取菜单详情
     * @param menuId 菜单id
     * @return 返回结果
     */
    SysMenuVo getMenuById(Long menuId);

    /**
     * 通过用户id获取菜单信息
     * @param userId 用户id
     * @return 用户菜单信息
     */
    Set<String> getMenuPermsByUserId(Long userId);


    /**
     * 通过用户id获取当前路由信息
     * @param userId 用户id
     * @return 用户路由信息
     */
    List<SysMenuInfoVo> getRoutesByUserId(Long userId);

    /**
     * 获取菜单树
     * @return 菜单树
     */
    List<MenuTreeVo> getMenuTree();
}
