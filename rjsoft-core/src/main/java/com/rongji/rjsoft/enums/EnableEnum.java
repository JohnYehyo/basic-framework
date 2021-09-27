package com.rongji.rjsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 用户状态
 * @author: JohnYehyo
 * @create: 2021-04-26 14:44:22
 */
@Getter
@AllArgsConstructor
public enum EnableEnum {

    ENABLE(0, "启用"),
    DISABLE(1, "禁用"),
    DELETED(2, "删除");

    private int code;
    private String content;
}
