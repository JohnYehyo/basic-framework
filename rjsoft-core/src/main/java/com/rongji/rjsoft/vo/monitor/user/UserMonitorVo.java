package com.rongji.rjsoft.vo.monitor.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 用户监控视图
 * @author: JohnYehyo
 * @create: 2021-09-06 11:16:17
 */
@Data
public class UserMonitorVo implements Serializable {


    private static final long serialVersionUID = 2012404807076594842L;

    /**
     * 会话编号
     */
    private String tokenId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ip;

//    /**
//     * 登录地址
//     */
//    private String loginLocation;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
}
