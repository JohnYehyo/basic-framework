package com.rongji.rjsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 日志操作状态
 * @author: JohnYehyo
 * @create: 2021-09-08 09:35:20
 */
@Getter
@AllArgsConstructor
public enum LogStatusEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    FAIL(1, "失败"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(0, "登录成功"),

    /**
     * 登录失败
     */
    LOGIN_FAIL(1, "登录失败"),

    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(0, "退出成功"),

    /**
     * 退出失败
     */
    LOGOUT_FAIL(1, "退出失败");

    private int code;

    private String value;
}
