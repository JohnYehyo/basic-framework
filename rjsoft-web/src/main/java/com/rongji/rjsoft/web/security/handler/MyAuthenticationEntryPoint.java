package com.rongji.rjsoft.web.security.handler;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.vo.ResponseVo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @description: 认证失败处理类
 * @author: JohnYehyo
 * @create: 2021-04-26 11:20:13
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

    private static final long serialVersionUID = -1345249413362834725L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        //当用户尝试访问安全的REST资源而不提供任何凭据时(未授权)，将调用此方法发送响应
        ServletUtils.response(response, JSON.toJSONString(ResponseVo.response(ResponseEnum.UNAUTHORIZED)),
                ResponseEnum.UNAUTHORIZED.getCode());
    }
}
