package com.rongji.rjsoft.web.security.handler;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.enums.LogStatusEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.service.ISysLoginInfoService;
import com.rongji.rjsoft.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义退出处理类
 * @author: JohnYehyo
 * @create: 2021-04-26 11:36:41
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ISysLoginInfoService sysLoginInfoService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        LoginUser loginUser = tokenUtils.getLoginUser(request);
        if (null != loginUser) {
            String userName = loginUser.getUsername();
            //删除用户缓存记录
            tokenUtils.deleteLoginUser(loginUser.getToken());
            sysLoginInfoService.saveLoginInfo(userName, LogStatusEnum.LOGOUT_SUCCESS.getCode(), LogStatusEnum.LOGOUT_SUCCESS.getValue());
        }
        ServletUtils.response(response, JSON.toJSONString(ResponseVo.success("退出成功")), ResponseEnum.SUCCESSFUL.getCode());
    }
}
