package com.rongji.rjsoft.query.system.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色下拉查询
 * @author: JohnYehyo
 * @create: 2021-08-30 14:17:33
 */
@Data
public class RoleSelectQuery {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
