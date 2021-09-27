package com.rongji.rjsoft.vo.system.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 角色下拉视图
 * @author: JohnYehyo
 * @create: 2021-09-02 14:20:39
 */
@Data
public class SysRoleSelectVo implements Serializable {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;
}
