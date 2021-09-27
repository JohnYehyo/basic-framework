package com.rongji.rjsoft.service;

import com.rongji.rjsoft.query.monitor.user.UserMonitorQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.monitor.user.UserMonitorVo;

/**
 * @description: 用户监控
 * @author: JohnYehyo
 * @create: 2021-09-06 11:20:49
 */
public interface IUserMonitorService {

    /**
     * 用户监控列表
     * @param userMonitorQuery 查询条件
     * @return 用户监控列表
     */
    CommonPage<UserMonitorVo> getListOfUser(UserMonitorQuery userMonitorQuery);
}
