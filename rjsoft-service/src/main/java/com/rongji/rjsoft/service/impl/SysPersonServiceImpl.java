package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.rongji.rjsoft.ao.system.PasswordAo;
import com.rongji.rjsoft.ao.system.PersonInfoAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.SecurityUtils;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.entity.system.SysDept;
import com.rongji.rjsoft.entity.system.SysRole;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.mapper.SysRoleMapper;
import com.rongji.rjsoft.mapper.SysUserMapper;
import com.rongji.rjsoft.service.ISysPersonService;
import com.rongji.rjsoft.service.ISysRoleService;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.person.PersonInfoVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description: 个人中心
 * @author: JohnYehyo
 * @create: 2021-09-09 16:27:09
 */
@Service
@AllArgsConstructor
public class SysPersonServiceImpl implements ISysPersonService {

    private final TokenUtils tokenUtils;

    private final ISysRoleService sysRoleService;

    private final SysRoleMapper sysRoleMapper;

    private final SysUserMapper sysUserMapper;

    private static final String PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&_*-])[\\da-zA-Z~!@#$%^&_*-]{8,}$";

    /**
     * 个人信息
     *
     * @return 个人信息
     */
    @Override
    public PersonInfoVo getPerson() {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        PersonInfoVo personInfoVo = new PersonInfoVo();
        BeanUtil.copyProperties(user, personInfoVo);
        SysDept sysDept = loginUser.getSysDept();
        if (null != sysDept) {
            personInfoVo.setDeptName(sysDept.getDeptName());
        }
        if (!CollectionUtil.isEmpty(loginUser.getRoles())) {
            List<SysRole> list = sysRoleMapper.selectBatchIds(loginUser.getRoles());
            String roleNames = list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
            personInfoVo.setRoleName(roleNames);
        }
        return personInfoVo;
    }

    /**
     * 检查手机号码是否存在
     *
     * @param user         用户信息
     * @param personInfoAo 个人信息
     * @return 结果
     */
    private boolean checkUserByPhone(SysUser user, PersonInfoAo personInfoAo) {
        SysUser sysUser = sysUserMapper.checkUserByPhone(personInfoAo.getPhonenumber());
        //排除本用户修改自己手机号
        if (null != sysUser && !sysUser.getUserId().equals(user.getUserId())) {
            return true;
        }
        return false;
    }

    /**
     * 检查邮箱是否存在
     *
     * @param user         用户信息
     * @param personInfoAo 个人信息
     * @return 结果
     */
    private boolean checkUserByEmail(SysUser user, PersonInfoAo personInfoAo) {
        SysUser sysUser = sysUserMapper.checkUserByEmail(personInfoAo.getEmail());
        //排除本用户修改自己email
        if (null != sysUser && !sysUser.getUserId().equals(user.getUserId())) {
            return true;
        }
        return false;
    }

    /**
     * 修改个人信息
     *
     * @param personInfoAo 个人信息
     * @return 个人信息
     */
    @Override
    public ResponseVo update(PersonInfoAo personInfoAo) {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        //手机号、邮箱是否存在
        if (StringUtils.isNotEmpty(personInfoAo.getPhonenumber()) && checkUserByPhone(user, personInfoAo)) {
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "手机号已存在!");
        }
        if (StringUtils.isNotEmpty(personInfoAo.getEmail()) && checkUserByEmail(user, personInfoAo)) {
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "邮箱存在!");
        }

        BeanUtil.copyProperties(personInfoAo, user);
        user.setUpdateBy(user.getUserName());
        user.setUpdateTime(LocalDateTime.now());
        if (sysUserMapper.updateUserById(user) > 0) {
            loginUser.setUser(user);
            tokenUtils.setLoginUser(loginUser);
            return ResponseVo.success("修改信息成功");
        }
        return ResponseVo.error("修改信息失败");
    }

    /**
     * 修改密码
     *
     * @param passwordAo 参数
     * @return 修改结果
     */
    @Override
    public ResponseVo updatePassWord(PasswordAo passwordAo) {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        String encryptNewPassword = SecurityUtils.encryptPassword(passwordAo.getNewPassword());
        if (!matchesPassword(passwordAo.getOldPassword(), user.getPassword())) {
            return ResponseVo.response(ResponseEnum.ERROR_PASSWORD);
        }
        if (!passwordAo.getNewPassword().equals(passwordAo.getRepeatPassword())) {
            return ResponseVo.response(ResponseEnum.INCONSISTENT_PASSWORD);
        }
        if (matchesPassword(passwordAo.getNewPassword(), user.getPassword())) {
            return ResponseVo.response(ResponseEnum.SAME_OLD_PASSWORD);
        }
        if (!Pattern.matches(PATTERN, passwordAo.getNewPassword())) {
            return ResponseVo.response(ResponseEnum.EASY_PASSWORD);
        }
        user.setPassword(encryptNewPassword);
        user.setUpdateBy(user.getUserName());
        user.setUpdateTime(LocalDateTime.now());
        if (sysUserMapper.updatePassword(user) > 0) {
            loginUser.setUser(user);
            tokenUtils.setLoginUser(loginUser);
            return ResponseVo.success("修改密码成功");
        }
        return ResponseVo.error("修改密码失败");
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
