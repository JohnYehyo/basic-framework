package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 个人信息表单
 * @author: JohnYehyo
 * @create: 2021-09-09 16:54:18
 */
@Data
public class PersonInfoAo {

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private int gender;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "电话号码")
    private String phonenumber;

}
