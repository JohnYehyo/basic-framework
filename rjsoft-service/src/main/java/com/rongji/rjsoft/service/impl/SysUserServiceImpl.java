package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.system.SysUserAo;
import com.rongji.rjsoft.common.security.util.SecurityUtils;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.entity.system.SysUserPost;
import com.rongji.rjsoft.entity.system.SysUserRole;
import com.rongji.rjsoft.mapper.SysDeptMapper;
import com.rongji.rjsoft.mapper.SysUserMapper;
import com.rongji.rjsoft.mapper.SysUserPostMapper;
import com.rongji.rjsoft.mapper.SysUserRoleMapper;
import com.rongji.rjsoft.query.system.user.UserQuery;
import com.rongji.rjsoft.service.ISysUserService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.user.SysUserInfoVo;
import com.rongji.rjsoft.vo.system.user.SysUserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-04-26
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserMapper sysUserMapper;

    private final SysUserPostMapper sysUserPostMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysDeptMapper sysDeptMapper;


    /**
     * 通过用户姓名查询用户信息
     *
     * @param userName 用户姓名
     * @return 用户信息
     */
    @Override
    public SysUser getUserByName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, userName);
        return sysUserMapper.selectOne(queryWrapper);
    }


    /**
     * 查询用户列表
     *
     * @param userQuery 查询条件
     * @return 用户信息
     */
    @Override
    public CommonPage<SysUserVo> listOfUser(UserQuery userQuery) {
        if (userQuery.getCurrent() == null) {
            userQuery.setCurrent(1);
        }
        if (userQuery.getPageSize() == null) {
            userQuery.setPageSize(10);
        }
        //部门id查询条件不为空 通过部门id查询所有下级的部门id
        if (null != userQuery.getDeptId()) {
            List<Long> deptList = sysDeptMapper.selectDeptIdSByDeptId(userQuery.getDeptId());
            deptList.add(userQuery.getDeptId());
            userQuery.setDeptIds(deptList);
        }
        IPage<SysUserVo> userPages = new Page<>(userQuery.getCurrent(), userQuery.getPageSize());
        userPages = sysUserMapper.listOfUser(userPages, userQuery);
        return CommonPageUtils.assemblyPage(userPages);
    }

    /**
     * 新增用户
     *
     * @param user 用户表单信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUser(SysUserAo user) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(user, sysUser);
        sysUser.setPassword(SecurityUtils.encryptPassword(Constants.DEFAULT_PASSWORD));
        sysUser.setCreateBy(SecurityUtils.getUserName());
        int insert = sysUserMapper.saveUser(sysUser);

        user.setUserId(sysUser.getUserId());

        saveUserWithPost(user);

        saveUserWithRole(user);

        return insert;
    }

    /**
     * 新增用户角色关系
     *
     * @param user 用户表单信息
     */
    private void saveUserWithRole(SysUserAo user) {
        Long[] roles = user.getRoleIds();
        if (null != roles && roles.length > 0) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<>();
            SysUserRole ur;
            for (Long roleId : roles) {
                ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                sysUserRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位关系
     *
     * @param user 用户表单信息
     */
    private void saveUserWithPost(SysUserAo user) {
        Long[] posts = user.getPostIds();
        if (null != posts && posts.length > 0) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<>();
            SysUserPost up;
            for (Long postId : posts) {
                up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                sysUserPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 编辑用户
     *
     * @param user 用户表单信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editUser(SysUserAo user) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(user, sysUser);

        deleteUserWithRole(user.getUserId());
        saveUserWithRole(user);

        deleteUserWithPost(user.getUserId());
        saveUserWithPost(user);

        return sysUserMapper.updateById(sysUser);
    }

    /**
     * 删除用户岗位关系
     *
     * @param id 用户信息
     */
    private void deleteUserWithPost(Long id) {
        LambdaUpdateWrapper<SysUserPost> pWrapper = new LambdaUpdateWrapper<>();
        pWrapper.eq(SysUserPost::getUserId, id);
        sysUserPostMapper.delete(pWrapper);
    }

    /**
     * 删除用户角色关系
     *
     * @param id 用户信息
     */
    private void deleteUserWithRole(Long id) {
        LambdaUpdateWrapper<SysUserRole> rWrapper = new LambdaUpdateWrapper<>();
        rWrapper.eq(SysUserRole::getUserId, id);
        sysUserRoleMapper.delete(rWrapper);
    }

    /**
     * 删除用户
     *
     * @param userIds 用户id
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUser(Long[] userIds) {
        List<Long> ids = Arrays.asList(userIds);
        sysUserRoleMapper.deleteBatchIds(ids);
        sysUserPostMapper.deleteBatchIds(ids);
        return sysUserMapper.batchUpdateDeleteMarks(ids);
    }

    /**
     * 检查用户名
     *
     * @param user 用户信息
     * @return boolean
     */
    @Override
    public boolean checkUserByUserName(SysUserAo user) {
        int isExist = sysUserMapper.checkUserByUserName(user.getUserName());
        return isExist > 0;
    }

    /**
     * 检查手机号
     *
     * @param user 用户信息
     * @return boolean
     */
    @Override
    public boolean checkUserByPhone(SysUserAo user) {
        SysUser sysUser = sysUserMapper.checkUserByPhone(user.getPhonenumber());
        //排除本用户修改自己手机号
        if (null != sysUser && !sysUser.getUserId().equals(user.getUserId())) {
            return true;
        }
        return false;
    }

    /**
     * 检查email
     *
     * @param user 用户信息
     * @return boolean
     */
    @Override
    public boolean checkUserByEmail(SysUserAo user) {
        SysUser sysUser = sysUserMapper.checkUserByEmail(user.getEmail());
        //排除本用户修改自己email
        if (null != sysUser && !sysUser.getUserId().equals(user.getUserId())) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public SysUserInfoVo getUserInfoById(Long userId) {
        List<Integer> roles = sysUserRoleMapper.getRolesByUserId(userId);
        List<Integer> posts = sysUserPostMapper.getPostsByUserId(userId);
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setRoleIds(roles);
        sysUserInfoVo.setPostIds(posts);
        return sysUserInfoVo;
    }

}
