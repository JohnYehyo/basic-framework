package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.entity.system.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 通多用户id查询角色信息
     * @param userId 用户id
     * @return 用户角色
     */
    List<Integer> getRolesByUserId(Long userId);

}
