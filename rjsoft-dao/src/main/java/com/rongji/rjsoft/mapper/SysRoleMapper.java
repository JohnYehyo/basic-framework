package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.query.system.role.RoleQuery;
import com.rongji.rjsoft.query.system.role.RoleSelectQuery;
import com.rongji.rjsoft.vo.system.role.SysRoleInfoVo;
import com.rongji.rjsoft.vo.system.role.SysRoleSelectVo;
import com.rongji.rjsoft.vo.system.role.SysRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-17
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过角色名查询角色
     * @param roleName 角色名
     * @return 返回结果
     */
    SysRole checkRoleByRoleName(String roleName);

    /**
     * 检查角色权限是否存在
     * @param roleKey 角色权限
     * @return 返回结果
     */
    SysRole checkRoleByRoleKey(String roleKey);

    /**
     * 角色列表分页查询
     * @param rolePages 分页对象
     * @param roleQuery 查询条件
     * @return 分页结果
     */
    IPage<SysRoleVo> pagesOfRole(IPage<SysRoleVo> rolePages, @Param("params") RoleQuery roleQuery);

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
    List<String> getRoleKeysByUserId(Long userId);

    /**
     * 查询角色列表
     * @param roleSelectQuery 查询条件
     * @return 角色列表
     */
    List<SysRoleSelectVo> listOfRole(RoleSelectQuery roleSelectQuery);

    /**
     * 保存角色信息
     * @param sysRole 保存角色信息
     * @return 保存结果
     */
    int saveRole(SysRole sysRole);
}
