package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.system.SysMenuAo;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.vo.system.menu.SysMenuInfoVo;
import com.rongji.rjsoft.entity.system.SysMenu;
import com.rongji.rjsoft.mapper.SysMenuMapper;
import com.rongji.rjsoft.query.system.menu.MenuQuery;
import com.rongji.rjsoft.service.ISysMenuService;
import com.rongji.rjsoft.vo.system.menu.SysMenuVo;
import com.rongji.rjsoft.vo.system.menu.MenuTreeVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    private final SysMenuMapper sysMenuMapper;

    /**
     * 通过菜单名检查菜单是否存在
     *
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    @Override
    public boolean checkMenuByName(SysMenuAo sysMenuAo) {
        SysMenu sysMenu = sysMenuMapper.checkMenuByName(sysMenuAo);
        if (null != sysMenu && !sysMenu.getMenuId().equals(sysMenuAo.getMenuId())) {
            return true;
        }
        return false;
    }

    /**
     * 保存菜单
     *
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    @Override
    public int saveMenu(SysMenuAo sysMenuAo) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuAo, sysMenu);
        return sysMenuMapper.insert(sysMenu);
    }

    /**
     * 编辑菜单
     *
     * @param sysMenuAo 菜单信息
     * @return 返回结果
     */
    @Override
    public int editMenu(SysMenuAo sysMenuAo) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuAo, sysMenu);
        return sysMenuMapper.updateById(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param menuIds 菜单id
     * @return 返回结果
     */
    @Override
    public int deleteMenu(Long[] menuIds) {
        return sysMenuMapper.deleteBatchIds(Arrays.asList(menuIds));
    }

    /**
     * 菜单列表查询
     *
     * @param menuQuery 查询参数
     * @return 返回结果
     */
    @Override
    public List<SysMenuInfoVo> listOfMenu(MenuQuery menuQuery) {
        List<SysMenuInfoVo> list;
        // 管理员显示所有菜单信息
        if (Constants.ADMIN_ID.equals(menuQuery.getUserId())) {
            list = sysMenuMapper.selectMenuList(menuQuery);
        }else{
            list = sysMenuMapper.selectMenuListByUserId(menuQuery);
        }
        //处理菜单数据结构
        return getSysMenuInfoVos(list);
    }

    private List<SysMenuInfoVo> getSysMenuInfoVos(List<SysMenuInfoVo> list) {
        //顶级路由
        List<SysMenuInfoVo> top = list.stream().filter(k -> k.getParentId() == 0).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(top)){
            return list;
        }
        //子级路由递归设置
        List<SysMenuInfoVo> menus = new ArrayList<>();
        for (SysMenuInfoVo parent : top) {
            setChildrenMenu(parent, list);
            menus.add(parent);
        }
        return menus;
    }


    /**
     * 通过id获取菜单详情
     *
     * @param menuId 菜单id
     * @return 返回结果
     */
    @Override
    public SysMenuVo getMenuById(Long menuId) {
        return sysMenuMapper.getMenuById(menuId);
    }

    /**
     * 通过用户id获取菜单信息
     *
     * @param userId 用户id
     * @return 用户菜单信息
     */
    @Override
    public Set<String> getMenuPermsByUserId(Long userId) {
        Set<String> menus = new HashSet<>();
        if (Constants.ADMIN_ID.equals(userId)) {
            menus.add("*:*:*");
            menus.add("system:user:delete");
            return menus;
        }
        List<String> list = sysMenuMapper.getMenuPermsByUserId(userId);
        if (CollectionUtil.isEmpty(list)) {
            return menus;
        }
        list.forEach(k -> {
            if (null != k) {
                menus.addAll(Arrays.asList(k.trim().split(",")));
            }
        });
        return menus;
    }

    /**
     * 通过用户id获取用户路由信息
     * @param userId 用户id
     * @return 用户路由信息
     */
    @Override
    public List<SysMenuInfoVo> getRoutesByUserId(Long userId) {
        List<SysMenuInfoVo> list;
        if(Constants.ADMIN_ID.equals(userId)){
            list = sysMenuMapper.getAllRoutes();
        }else {
            list = sysMenuMapper.getRoutesByUserId(userId);
        }
        //处理菜单数据结构
        return getSysMenuInfoVos(list);
    }

    /**
     * 递归设置子路由
     * @param parent 父级菜单
     * @param list 查询出的所有菜单
     */
    private void setChildrenMenu(SysMenuInfoVo parent, List<SysMenuInfoVo> list){
        List<SysMenuInfoVo> children = new ArrayList<>();
        for (SysMenuInfoVo child : list) {
            if(child.getParentId().longValue() == parent.getMenuId()){
                children.add(child);
            }
        }
        parent.setChildren(children);
        for (SysMenuInfoVo menu : children) {
            setChildrenMenu(menu, list);
        }
    }


    /**
     * 获取菜单树
     * @return 菜单树
     */
    @Override
    public List<MenuTreeVo> getMenuTree() {
        List<MenuTreeVo> list = sysMenuMapper.selectAllMenu();
        //处理菜单数据结构
        //顶级路由
        List<MenuTreeVo> top = list.stream().filter(k -> k.getParentId() == 0).collect(Collectors.toList());
        //子级路由递归设置
        List<MenuTreeVo> menus = new ArrayList<>();
        for (MenuTreeVo parent : top) {
            setChildrenTreeMenu(parent, list);
            menus.add(parent);
        }

        return menus;
    }

    private void setChildrenTreeMenu(MenuTreeVo parent, List<MenuTreeVo> list){
        List<MenuTreeVo> children = new ArrayList<>();
        for (MenuTreeVo child : list) {
            if(child.getParentId().longValue() == parent.getMenuId()){
                children.add(child);
            }
        }
        parent.setChildren(children);
        for (MenuTreeVo menu : children) {
            setChildrenTreeMenu(menu, list);
        }
    }

}
