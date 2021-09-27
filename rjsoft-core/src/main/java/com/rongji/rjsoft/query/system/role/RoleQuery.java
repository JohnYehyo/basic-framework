package com.rongji.rjsoft.query.system.role;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @description: 角色查询
 * @author: JohnYehyo
 * @create: 2021-08-30 14:17:33
 */
@Data
public class RoleQuery extends PageQuery {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态")
    private int status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "开始时间", example = "2021-01-31 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    /**
     * 截止时间
     */
    @ApiModelProperty(value = "截止时间", example = "2021-04-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
