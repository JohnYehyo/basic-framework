package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.entity.system.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量新增用户角色关系
     * @param list 用户信息
     */
    void batchUserRole(List<SysUserRole> list);

    /**
     * 通多用户id查询角色信息
     * @param userId 用户id
     * @return 用户角色
     */
    List<Integer> getRolesByUserId(Long userId);
}
