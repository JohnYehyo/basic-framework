package com.rongji.rjsoft.dto.system.role;

import com.rongji.rjsoft.entity.system.SysRole;
import lombok.Data;

/**
 * @description: 角色
 * @author: JohnYehyo
 * @create: 2021-09-01 11:01:02
 */
@Data
public class SysRoleDto extends SysRole {

    /**
     * 菜单组
     */
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    private Long[] deptIds;
}
