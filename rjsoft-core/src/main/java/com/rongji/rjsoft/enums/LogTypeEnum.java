package com.rongji.rjsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 日志操作类型
 * @author: JohnYehyo
 * @create: 2021-09-08 09:35:20
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnum {

    /**
     * 添加
     */
    INSERT(1, "新增"),

    /**
     * 编辑
     */
    UPDATE(2, "编辑"),

    /**
     * 删除
     */
    DELETE(3, "删除"),

    /**
     * 授权
     */
    AUTHORIZATION(4, "授权"),

    /**
     * 强制退出
     */
    FORCE(5, "强制退出"),

    /**
     * 上传
     */
    UPLOAD(6, "上传"),

    /**
     * 下载
     */
    DOWNLOAD(7, "下载"),

    /**
     * 启用
     */
    ENABLE(8, "启用"),

    /**
     * 禁用
     */
    DISBLE(9, "禁用"),

    /**
     * 查看
     */
    SELECT(10, "查看"),

    /**
     * 其它
     */
    OTHER(0, "其它");

    private int code;

    private String value;
}
