package com.rongji.rjsoft.vo.system.person;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 个人信息视图
 * @author: JohnYehyo
 * @create: 2021-09-09 16:29:36
 */
@Data
public class PersonInfoVo implements Serializable {

    private static final long serialVersionUID = 5395046390040032214L;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private int gender;

    /**
     * 部门
     */
    private String deptName;

    /**
     * 角色
     */
    private String roleName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;
}
