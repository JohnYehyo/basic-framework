package com.rongji.rjsoft.vo.system.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 角色详细信息
 * @author: JohnYehyo
 * @create: 2021-08-30 18:54:56
 */
@Data
public class SysRoleInfoVo extends SysRoleVo implements Serializable {
    private static final long serialVersionUID = 7922284540711386871L;

    /**
     * 菜单组
     */
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    private Long[] deptIds;
}
