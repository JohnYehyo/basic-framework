package com.rongji.rjsoft.vo.system.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @description: 用户查询视图
 * @author: JohnYehyo
 * @create: 2021-08-17 10:13:23
 */
@Data
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = -5093291313687994040L;

    /**
     * 用户id
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
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private int gender;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 个人头像
     */
    private String avatar;
}
