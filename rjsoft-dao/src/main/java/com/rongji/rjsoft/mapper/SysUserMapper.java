package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.query.system.user.UserQuery;
import com.rongji.rjsoft.vo.system.user.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-04-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     * @param userPages 分页对象
     * @param userQuery 查询条件
     * @return 分页结果
     */
    IPage<SysUserVo> listOfUser(IPage<SysUserVo> userPages, @Param("params") UserQuery userQuery);

    /**
     * 检查用户名
     * @param userName 用户名
     * @return int
     */
    int checkUserByUserName(String userName);

    /**
     * 检查手机号
     * @param phonenumber 手机号
     * @return int
     */
    SysUser checkUserByPhone(String phonenumber);

    /**
     * 检查email
     * @param email email
     * @return int
     */
    SysUser checkUserByEmail(String email);

    /**
     * 批量更新删除标记
     * @param ids 菜单id
     * @return 删除结果
     */
    int batchUpdateDeleteMarks(List<Long> ids);

    /**
     * 保存用户
     * @param sysUser 用户信息
     * @return 保存结果
     */
    int saveUser(SysUser sysUser);

    /**
     * 修改个人信息
     * @param user 个人信息
     * @return 修改结果
     */
    int updateUserById(SysUser user);

    /**
     *  修改密码
     * @param user 新密码
     * @return 修改结果
     */
    int updatePassword(SysUser user);
}
