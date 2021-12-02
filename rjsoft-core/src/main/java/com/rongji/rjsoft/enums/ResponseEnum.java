package com.rongji.rjsoft.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 响应枚举
 * @Author JohnYehyo
 * @Date 2021-03-02 14:56
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败，请稍后重试"),
    SUCCESSFUL(200, "响应成功"),
    UNAUTHORIZED(401, "请登陆"),
    LOGIN_ERROR(402, "用户名或密码错误"),
    NO_DATA(404, "无相关数据"),
    PARAM_ERROR(405, "参数错误"),
    CAPTCHA_EXPIRED(406, "验证码已过期"),
    CAPTCHA_ERROR(407, "验证码错误"),
    CAPTCHA_FAIL(408, "验证码获取失败"),
    ERROR_PASSWORD(409, "原密码错误"),
    INCONSISTENT_PASSWORD(410, "两次输入新密码不一致"),
    SAME_OLD_PASSWORD(411, "新密码和老密码不能相同"),
    EASY_PASSWORD(412, "密码需包含数字、英文、特殊符号且长度大于8位"),
    ENCRYPTION_TO_DECRYPT(413, "加解密失败"),
    BUSY(429, "服务器繁忙，请稍后再试"),
    REPEAT_ACTION(430, "请勿重复提交"),
    EXCEPTION(1111, "系统异常，请稍后重试"),
    THIRD_PARTY_ERROR(503, "第三方系统异常"),
    USERCENTER_INVOKE_ERROR(505, "用户中心调用系统异常"),
    USER_INFO_MISS(507, "未查询到用户组织或角色等信息"),
    USER_ORGANIZATION_INFO_MUTI(508, "查询到多个用户组织信息"),
    USER_ROLE_INFO_MUTI(509, "查询到多个用户角色信息"),
    USER_ORGANIZATION_NULL(1000, "用户组织单位为空"),
    USER_ROLE_NULL(1001, "用户角色为空"),
    USER_PERMISSION_NULL(1002, "用户权限为空"),
    USER_INFO_NULL(1003, "用户信息为空"),
    TOKEN_INVALID(1004, "token无效"),
    NO_PERMISSION(1005, "无权限操作"),
    TOKEN_GET_NULL(1007, "无法获取有效token"),
    NO_PERMISSION_ACTION(1008, "无数据或无权操作"),
    USER_DISABLE(1009, "此账号已被禁用"),
    USER_DELETE(1010, "此账号已被删除"),
    NO_DEPT(1011, "请先为账号绑定部门"),
    SUPER_LONG_FILE(4000, "文件名超长"),
    SUPER_LARGE_FILE(4001, "文件超出大小限制"),
    NO_ALLOW_FILE(4002, "不支持的文件类型"),
    NO_ALLOW_WORD(4003, "检测文章标题、描述活内容中包含敏感词:"),
    FILE_UPLOAD_ERROR(4004, "文件上传失败"),
    INDEX_INSERT_EDIT_ERROR(5001, "新增/更新索引失败"),
    INDEX_DELETE_ERROR(5002, "删除索引失败"),
    DOC_INSERT_EDIT_ERROR(5003, "新增/更新文档失败"),
    DOC_DELETE_ERROR(5004, "删除文档失败"),
    NONE_SELECT_PARAM(5005, "请输入查询条件"),
    CONNECTION_EXCEPTION(11111, "连接异常, 请尽快上线查看!"),
    DATABASE_EXCEPTION(22222, "连接数据库异常, 请尽快上线查看!"),
    REDIS_EXCEPTION(33332, "连接REDIS异常, 请尽快上线查看!");

    private Integer code;
    private String value;
}
