package com.rongji.rjsoft.service;

import com.rongji.rjsoft.ao.system.LoginAo;

/**
 * @description: 登录退出
 * @author: JohnYehyo
 * @create: 2021-04-26 17:28:25
 */
public interface ISysLoginService {

    /**
     * 登录
     * @param loginAo 请求参数体
     * @return 登录情况
     */
    String login(LoginAo loginAo);
}
