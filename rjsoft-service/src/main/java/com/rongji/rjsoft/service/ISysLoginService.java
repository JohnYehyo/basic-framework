package com.rongji.rjsoft.service;

import com.rongji.rjsoft.ao.system.LoginAo;
import com.rongji.rjsoft.common.security.entity.SsoLoginUser;
import com.rongji.rjsoft.vo.ResponseVo;

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
    ResponseVo login(LoginAo loginAo);

    /**
     * 创建SSO token
     * @return 创建SSO token
     */
    String creatSsoToken();

    /**
     * token登录
     * @param token token
     * @return 登录结果
     */
    SsoLoginUser tokenLogin(String token);

}
