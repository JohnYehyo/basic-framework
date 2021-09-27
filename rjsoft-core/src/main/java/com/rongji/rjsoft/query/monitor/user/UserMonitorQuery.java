package com.rongji.rjsoft.query.monitor.user;

import com.rongji.rjsoft.query.common.PageQuery;
import lombok.Data;

/**
 * @description: 用户监控查询
 * @author: JohnYehyo
 * @create: 2021-09-06 11:04:55
 */
@Data
public class UserMonitorQuery extends PageQuery {

    /**
     * 登录地址
     */
    private String ip;

    /**
     * 用户名称
     */
    private String userName;
}
