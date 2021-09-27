package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: bumenchuandicanshu
 * @author: JohnYehyo
 * @create: 2021-09-02 11:14:10
 */
@Data
public class SysDeptAo {

    public interface insert{

    }

    public interface update{

    }

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id", required = true)
    @NotNull(
            groups = {SysDeptAo.update.class},
            message = "部门不能缺失"
    )
    private Long deptId;

    /**
     * 父级部门id
     */
    @ApiModelProperty(value = "父级部门id", required = true)
    @NotEmpty(groups = {SysDeptAo.insert.class, SysDeptAo.update.class}, message = "上级部门不能缺失")
    private Long parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", required = true)
    @NotEmpty(groups = {SysDeptAo.insert.class, SysDeptAo.update.class}, message = "部门名称不能为空")
    private String deptName;

    /**
     * 排序序号
     */
    @ApiModelProperty(value = "排序序号", required = true)
    @NotNull(groups = {SysDeptAo.insert.class, SysDeptAo.update.class}, message = "排序序号不能为缺失")
    private int orderNum;

    /**
     * 部门状态
     */
    @ApiModelProperty(value = "部门状态")
    private int status;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 行政区划code
     */
    @ApiModelProperty(value = "行政区划code", required = true)
    @NotEmpty(groups = {SysDeptAo.insert.class, SysDeptAo.update.class}, message = "行政区划不能为空")
    private String branch_code;

    /**
     * 部门类型
     */
    @ApiModelProperty(value = "部门类型")
    private int dept_type;
}
