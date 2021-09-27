package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.system.SysRoleAo;
import com.rongji.rjsoft.entity.system.SysRole;
import com.rongji.rjsoft.query.system.role.RoleQuery;
import com.rongji.rjsoft.query.system.role.RoleSelectQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.role.SysRoleInfoVo;
import com.rongji.rjsoft.vo.system.role.SysRoleSelectVo;
import com.rongji.rjsoft.vo.system.role.SysRoleVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-17
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 检查角色名是否存在
     * @param sysRoleAo 角色信息
     * @return 角色权限
     */
    boolean checkRoleByRoleName(SysRoleAo sysRoleAo);

    /**
     * 检查角色权限是否存在
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    boolean checkRoleByRoleKey(SysRoleAo sysRoleAo);

    /**
     * 新增角色
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    int addRole(SysRoleAo sysRoleAo);

    /**
     * 编辑角色
     * @param sysRoleAo 角色信息
     * @return 返回结果
     */
    int editRole(SysRoleAo sysRoleAo);

    /**
     * 删除角色
     * @param roleIds 角色id
     * @return 返回结果
     */
    int deleteRole(Long[] roleIds);

    /**
     * 查询角色列表
     * @param roleQuery 查询条件
     * @return 返回结果
     */
    CommonPage<SysRoleVo> pagesOfRole(RoleQuery roleQuery);

    /**
     * 通过角色id查询角色详细信息
     * @param roleId 角色id
     * @return 返回结果
     */
    SysRoleInfoVo getRoleInfoById(Long roleId);

    /**
     * 通过用户id获取角色信息
     * @param userId 用户id
     * @return 用户角色信息
     */
    Set<String> getRoleKeysByUserId(Long userId);

    /**
     * 查询角色列表
     * @param roleSelectQuery 查询条件
     * @return 角色列表
     */
    List<SysRoleSelectVo> listOfRole(RoleSelectQuery roleSelectQuery);
}
