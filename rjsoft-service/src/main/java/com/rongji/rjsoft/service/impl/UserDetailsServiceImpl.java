package com.rongji.rjsoft.service.impl;

import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.entity.system.SysDept;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.enums.EnableEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.service.ISysDeptService;
import com.rongji.rjsoft.service.ISysMenuService;
import com.rongji.rjsoft.service.ISysRoleService;
import com.rongji.rjsoft.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @description: 用户验证处理
 * @author: JohnYehyo
 * @create: 2021-04-26 14:37:07
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserByName(username);
        if (null == user) {
            LogUtils.info("登录用户：{} 不存在.", username);
            throw new BadCredentialsException(ResponseEnum.USER_INFO_NULL.getValue());
        } else if (EnableEnum.DELETED.getCode() == user.getDelFlag()) {
            LogUtils.info("登录用户：{} 已被删除.", username);
            throw new BusinessException(ResponseEnum.USER_DELETE);
        } else if (EnableEnum.DISABLE.getCode() == user.getStatus()) {
            LogUtils.info("登录用户：{} 已被停用.", username);
            throw new BusinessException(ResponseEnum.USER_DISABLE);
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        SysDept sysDept = null;
        if (null != user.getDeptId()) {
            sysDept = sysDeptService.getById(user.getDeptId());

        }
        Set<String> roles = sysRoleService.getRoleKeysByUserId(user.getUserId());
        Set<String> perms = sysMenuService.getMenuPermsByUserId(user.getUserId());
        return new LoginUser(user, roles, perms, sysDept);
    }
}
