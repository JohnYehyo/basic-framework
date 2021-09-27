package com.rongji.rjsoft.query.system.user;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 用户查询
 * @author: JohnYehyo
 * @create: 2021-08-17 10:03:02
 */
@Data
public class UserQuery extends PageQuery {

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", dataType = "Long")
    private Long deptId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "账号", dataType = "String")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "昵称", dataType = "String")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty(value = "用户类型", dataType = "String")
    private String userType;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", dataType = "String")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String phonenumber;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "账号状态", dataType = "int")
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

    /**
     * 部门id集合
     */
    private List<Long> deptIds;

}
