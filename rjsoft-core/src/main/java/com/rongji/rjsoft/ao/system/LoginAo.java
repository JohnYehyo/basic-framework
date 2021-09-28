package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 登录传参
 * @author: JohnYehyo
 * @create: 2021-04-26 15:38:18
 */
@Data
public class LoginAo {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true)
    @NotEmpty(message = "验证码不能为空")
    private String captcha;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用Id")
    private String appId = "rjsoft";

    /**
     * uuid
     */
    @ApiModelProperty(value = "uuid", required = true)
    private String uuid;


}
