package com.rongji.rjsoft.common.security.entity;

import lombok.Data;

import java.util.Set;

/**
 * @description: SSO用户信息
 * @author: JohnYehyo
 * @create: 2021-11-25 09:47:36
 */
@Data
public class SsoLoginUser {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户唯一标识
     */
    private String token;


    /**
     * 登录IP
     */
    private String loginIp;


    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 权限列表
     */
    private Set<String> permissions;
}
