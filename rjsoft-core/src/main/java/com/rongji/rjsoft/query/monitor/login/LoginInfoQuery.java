package com.rongji.rjsoft.query.monitor.login;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @description: 访问记录查询
 * @author: JohnYehyo
 * @create: 2021-09-09 14:54:01
 */
@Data
public class LoginInfoQuery extends PageQuery {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

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
