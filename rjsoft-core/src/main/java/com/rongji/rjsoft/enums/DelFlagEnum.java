package com.rongji.rjsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 删除标记枚举
 * @author: JohnYehyo
 * @create: 2021-09-24 10:59:47
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum {

    exist(0, "存在"),
    remove(1, "删除");

    private int code;
    private String value;
}
