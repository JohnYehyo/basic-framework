package com.rongji.rjsoft.vo.system.dept;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 部门同步树
 * @author: JohnYehyo
 * @create: 2021-09-09 10:47:42
 */
@Data
public class SysDeptAllTreeInfoVo implements Serializable {

    private static final long serialVersionUID = 676873841324848348L;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 祖级列表
     */
    private String ancestors;

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

    /**
     * 行政区划code
     */
    private String branchCode;

    /**
     * 机构名称
     */
    private String branchName;

    /**
     * 子节点
     */
    private List<SysDeptAllTreeInfoVo> children;

}
