package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rongji.rjsoft.ao.system.SysRoleAo;
import com.rongji.rjsoft.common.security.util.SecurityUtils;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.entity.system.SysRole;
import com.rongji.rjsoft.entity.system.SysRoleMenu;
import com.rongji.rjsoft.mapper.SysRoleMapper;
import com.rongji.rjsoft.mapper.SysRoleMenuMapper;
import com.rongji.rjsoft.query.system.role.RoleQuery;
import com.rongji.rjsoft.query.system.role.RoleSelectQuery;
import com.rongji.rjsoft.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.role.SysRoleInfoVo;
import com.rongji.rjsoft.vo.system.role.SysRoleSelectVo;
import com.rongji.rjsoft.vo.system.role.SysRoleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-17
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 检查角色名是否存在
     *
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    @Override
    public boolean checkRoleByRoleName(SysRoleAo sysRoleAo) {
        SysRole sysRole = sysRoleMapper.checkRoleByRoleName(sysRoleAo.getRoleName());
        if (null != sysRole && !sysRole.getRoleId().equals(sysRoleAo.getRoleId())) {
            return true;
        }
        return false;
    }

    /**
     * 检查角色权限是否存在
     *
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    @Override
    public boolean checkRoleByRoleKey(SysRoleAo sysRoleAo) {
        SysRole sysRole = sysRoleMapper.checkRoleByRoleKey(sysRoleAo.getRoleKey());
        if (null != sysRole && !sysRole.getRoleId().equals(sysRoleAo.getRoleId())) {
            return true;
        }
        return false;
    }

    /**
     * 新增角色
     *
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addRole(SysRoleAo sysRoleAo) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleAo, sysRole);
        sysRole.setCreateBy(SecurityUtils.getUserName());
        sysRoleMapper.saveRole(sysRole);
        sysRoleAo.setRoleId(sysRole.getRoleId());
        // 新增角色菜单信息
        return saveRoleWithMenu(sysRoleAo);
    }

    /**
     * 新增角色菜单信息
     *
     * @param sysRole 角色信息
     * @return
     */
    private int saveRoleWithMenu(SysRoleAo sysRole) {
        int rows = 1;
        List<SysRoleMenu> list = new ArrayList<>();
        SysRoleMenu sysRoleMenu;
        for (Long menuId : sysRole.getMenuIds()) {
            sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            list.add(sysRoleMenu);
        }
        if (list.size() > 0) {
            rows = sysRoleMenuMapper.batchSaveRoleMenu(list);
        }
        return rows;
    }

    /**
     * 编辑角色
     *
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editRole(SysRoleAo sysRoleAo) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleAo, sysRole);
        sysRoleMapper.updateById(sysRole);
        //删除原角色菜单信息
        deleteRoleWithMenu(sysRole);
        //新增角色菜单信息
        return saveRoleWithMenu(sysRoleAo);
    }

    /**
     * 删除原角色菜单信息
     *
     * @param sysRole
     */
    private void deleteRoleWithMenu(SysRole sysRole) {
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper();
        wrapper.eq(SysRoleMenu::getRoleId, sysRole.getRoleId());
        sysRoleMenuMapper.delete(wrapper);
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色id
     * @return 返回结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRole(Long[] roleIds) {
        List<Long> ids = Arrays.asList(roleIds);
        //删除角色菜单信息
        sysRoleMenuMapper.deleteBatchIds(ids);
        //删除角色信息
        return sysRoleMapper.deleteBatchIds(ids);
    }

    /**
     * 查询角色列表
     *
     * @param roleQuery 查询条件
     * @return 返回结果
     */
    @Override
    public CommonPage<SysRoleVo> pagesOfRole(RoleQuery roleQuery) {
        if (roleQuery.getCurrent() == null) {
            roleQuery.setCurrent(1);
        }
        if (roleQuery.getPageSize() == null) {
            roleQuery.setPageSize(10);
        }
        IPage<SysRoleVo> rolePages = new Page<>(roleQuery.getCurrent(), roleQuery.getPageSize());
        rolePages = sysRoleMapper.pagesOfRole(rolePages, roleQuery);
        return CommonPageUtils.assemblyPage(rolePages);
    }

    /**
     * 通过角色id查询角色详细信息
     *
     * @param roleId 角色id
     * @return 返回结果
     */
    @Override
    public SysRoleInfoVo getRoleInfoById(Long roleId) {
        //通过角色查询菜单
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(wrapper);
        //通过id查询角色信息
        SysRoleInfoVo roleInfo = sysRoleMapper.getRoleInfoById(roleId);
        //合并结果
        Set<Long> collect = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
        Long[] menuIds = new Long[collect.size()];
        collect.toArray(menuIds);
        roleInfo.setMenuIds(menuIds);
        return roleInfo;
    }

    /**
     * 通过用户id获取角色信息
     *
     * @param userId 用户id
     * @return 用户角色信息
     */
    @Override
    public Set<String> getRoleKeysByUserId(Long userId) {
        Set<String> roles = new HashSet<>();
        if (Constants.ADMIN_ID.equals(userId)) {
            roles.add("admin");
            return roles;
        }
        List<String> list = sysRoleMapper.getRoleKeysByUserId(userId);
        if (CollectionUtil.isEmpty(list)) {
            return roles;
        }
        list.forEach(item -> {
            if(null!=item){
                roles.addAll(Arrays.asList(item.trim().split(",")));
            }
        });
        return roles;
    }

    /**
     * 查询角色列表
     * @param roleSelectQuery 查询条件
     * @return 角色列表
     */
    @Override
    public List<SysRoleSelectVo> listOfRole(RoleSelectQuery roleSelectQuery) {
        return sysRoleMapper.listOfRole(roleSelectQuery);
    }
}
