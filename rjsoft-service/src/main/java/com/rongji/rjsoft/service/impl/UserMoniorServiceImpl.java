package com.rongji.rjsoft.service.impl;

import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.rjsoft.common.util.TimeUtils;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.query.monitor.user.UserMonitorQuery;
import com.rongji.rjsoft.service.IUserMonitorService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.monitor.user.UserMonitorVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description: 用户监控
 * @author: JohnYehyo
 * @create: 2021-09-06 11:22:54
 */
@Service
@AllArgsConstructor
public class UserMoniorServiceImpl implements IUserMonitorService {


    private final RedisCache redisCache;

    /**
     * 用户监控列表
     *
     * @param userMonitorQuery 查询条件
     * @return 用户监控列表
     */
    @Override
    public CommonPage<UserMonitorVo> getListOfUser(UserMonitorQuery userMonitorQuery) {
        if (null == userMonitorQuery.getCurrent()) {
            userMonitorQuery.setCurrent(1);
        }
        if (null == userMonitorQuery.getPageSize()) {
            userMonitorQuery.setPageSize(10);
        }

        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");

        List<UserMonitorVo> list = new ArrayList<>();
        LoginUser loginUser;
        UserMonitorVo userMonitorVo;
        for (String key : keys) {
            loginUser = redisCache.getCacheObject(key);
            if (null == loginUser || null == loginUser.getUser()) {
                continue;
            }
            if (StringUtils.isNotEmpty(userMonitorQuery.getUserName())) {
                if (userMonitorQuery.getUserName().equals(loginUser.getUsername())) {
                    addList(list, loginUser);
                }
                continue;
            }
            addList(list, loginUser);

        }
        return manualPage(userMonitorQuery, list);
    }

    private CommonPage<UserMonitorVo> manualPage(UserMonitorQuery userMonitorQuery, List<UserMonitorVo> list) {
        CommonPage<UserMonitorVo> result = new CommonPage<>();
        int pageSize = list.size() % userMonitorQuery.getPageSize() > 0
                ? list.size() / userMonitorQuery.getPageSize() + 1 : userMonitorQuery.getPageSize();
        result.setTotalPage((long) pageSize);
        result.setTotal((long) list.size());
        result.setCurrent(Long.valueOf(userMonitorQuery.getCurrent()));
        result.setPageSize(Long.valueOf(userMonitorQuery.getPageSize()));
        result.setList(list);
        return result;
    }

    private void addList(List<UserMonitorVo> list, LoginUser loginUser) {
        UserMonitorVo userMonitorVo;
        userMonitorVo = new UserMonitorVo();
        userMonitorVo.setTokenId(loginUser.getToken());
        userMonitorVo.setUserName(loginUser.getUsername());
        userMonitorVo.setIp(loginUser.getLoginIp());
        userMonitorVo.setLoginTime(TimeUtils.long2LocaDateTime(loginUser.getLoginTime()));
        list.add(userMonitorVo);
    }

}
