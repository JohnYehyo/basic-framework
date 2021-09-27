package com.rongji.rjsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 操作人类别
 * @author: JohnYehyo
 * @create: 2021-09-08 09:51:13
 */
@Getter
@AllArgsConstructor
public enum OperatorTypeEnum {

    /**
     * web端
     */
    WEB(1, "WEB端"),

    /**
     * 移动端
     */
    MOBILE(2, "移动端"),

    /**
     * 其它
     */
    OTHER(0, "其它");

    private int code;
    private String value;
}
