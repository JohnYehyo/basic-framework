package com.rongji.rjsoft.ao.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 用户角色参数
 * @author: JohnYehyo
 * @create: 2021-08-30 16:55:34
 */
@Data
public class SysRoleAo {

    public interface update {
    }

    public interface add {
    }

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id")
    @NotNull(
            groups = {SysRoleAo.update.class},
            message = "角色id不能为空"
    )
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotEmpty(groups = {SysRoleAo.add.class, SysRoleAo.update.class}, message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @ApiModelProperty(value = "角色权限字符串", required = true)
    @NotEmpty(groups = {SysRoleAo.add.class, SysRoleAo.update.class}, message = "角色权限字符串不能为空")
    private String roleKey;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", required = true)
    @NotNull(groups = {SysRoleAo.add.class, SysRoleAo.update.class}, message = "显示顺序不能为空")
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @ApiModelProperty(value = "数据范围")
    private int dataScope;

    /**
     * 菜单树选择项是否关联显示
     */
    @ApiModelProperty(value = "菜单树选择项是否关联显示")
    private int menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示
     */
    @ApiModelProperty(value = "部门树选择项是否关联显示")
    private int deptCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态")
    private int status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 菜单组
     */
    @ApiModelProperty(value = "菜单")
    private Long[] menuIds;
}
