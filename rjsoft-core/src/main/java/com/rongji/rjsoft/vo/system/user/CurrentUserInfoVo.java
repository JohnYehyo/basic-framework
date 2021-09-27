package com.rongji.rjsoft.vo.system.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @description: 当前用户信息
 * @author: JohnYehyo
 * @create: 2021-08-31 10:25:25
 */
@Data
@AllArgsConstructor
public class CurrentUserInfoVo implements Serializable {

    /**
     * 用户信息
     */
    private SysUserVo sysUserVo;

    /**
     * 角色信息
     */
    private Set<String> roles;

    /**
     * 菜单权限信息
     */
    private Set<String> permissions;
}
