package com.rongji.rjsoft.vo.system.dept;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 部门信息视图
 * @author: JohnYehyo
 * @create: 2021-09-02 18:37:44
 */
@Data
public class SysDeptVo implements Serializable {

    private static final long serialVersionUID = -8280388573962070808L;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    private int status;

    /**
     * 部门类型
     */
    private int deptType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
