package com.rongji.rjsoft.ao.system;

import com.rongji.rjsoft.entity.system.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 用户表单参数
 * @author: JohnYehyo
 * @create: 2021-08-17 13:33:22
 */
@Data
public class SysUserAo {

    public interface update {
    }

    public interface Configuration {
    }

    public interface add {
    }

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id", required = false)
    @NotNull(
            groups = {SysUserAo.update.class},
            message = "用户id不能为空"
    )
    private Long userId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门id", required = false)
    private Long deptId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", required = true)
    @NotEmpty(groups = {SysUserAo.add.class, SysUserAo.update.class}, message = "用户名称不能为空")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", required = true)
    @NotEmpty(groups = {SysUserAo.add.class, SysUserAo.update.class}, message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty(value = "用户类型", required = false)
    private String userType;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", required = false)
    private String idCard;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", required = false)
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty(value = "性别", required = false)
    private int gender;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像", required = false)
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotEmpty(
            groups = {SysUserAo.Configuration.class},
            message = "用户密码不能为空"
    )
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "账号状态", required = false)
    private int status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志", required = false)
    private int delFlag;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    /**
     * 部门对象
     */
    @ApiModelProperty(value = "部门对象", required = false)
    private SysDept dept;

    /**
     * 角色组
     */
    @ApiModelProperty(value = "角色ids", required = false)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @ApiModelProperty(value = "岗位ids", required = false)
    private Long[] postIds;

}
