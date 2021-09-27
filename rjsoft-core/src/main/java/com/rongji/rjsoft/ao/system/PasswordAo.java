package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 密码修改
 * @author: JohnYehyo
 * @create: 2021-09-09 17:24:09
 */
@Data
public class PasswordAo {

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    /**
     * 重复密码
     */
    @ApiModelProperty(value = "重复密码", required = true)
    @NotEmpty(message = "重复密码不能为空")
    private String repeatPassword;

    /**
     * 原密码
     */
    @ApiModelProperty(value = "原密码", required = true)
    @NotEmpty(message = "原密码不能为空")
    private String oldPassword;
}
