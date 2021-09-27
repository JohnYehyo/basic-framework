package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.ao.system.SysMenuAo;
import com.rongji.rjsoft.vo.system.menu.SysMenuInfoVo;
import com.rongji.rjsoft.entity.system.SysMenu;
import com.rongji.rjsoft.query.system.menu.MenuQuery;
import com.rongji.rjsoft.vo.system.menu.SysMenuVo;
import com.rongji.rjsoft.vo.system.menu.MenuTreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过菜单名检查菜单是否存在
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    SysMenu checkMenuByName(@Param("params") SysMenuAo sysMenuAo);

    /**
     * 查询菜单
     * @param menuQuery 查询条件
     * @return 查询结果
     */
    List<SysMenuInfoVo> selectMenuList(@Param("params") MenuQuery menuQuery);

    /**
     * 通过用户id查询菜单
     * @param menuQuery 查询条件
     * @return 查询结果
     */
    List<SysMenuInfoVo> selectMenuListByUserId(@Param("params") MenuQuery menuQuery);

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
    List<String> getMenuPermsByUserId(Long userId);

    /**
     * 获取全部路由信息
     * @return 路由信息
     */
    List<SysMenuInfoVo> getAllRoutes();

    /**
     * 通过用户id获取路由信息
     * @param userId 用户id
     * @return 路由信息
     */
    List<SysMenuInfoVo> getRoutesByUserId(Long userId);

    /**
     * 获取菜单树
     * @return 菜单树
     */
    List<MenuTreeVo> selectAllMenu();
}
