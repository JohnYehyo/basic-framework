package com.rongji.rjsoft.web.security.handler;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.vo.ResponseVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 页面请求+ajax请求处理
 * @author: JohnYehyo
 * @create: 2021-04-26 11:17:25
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest requestequest, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ServletUtils.response(response, JSON.toJSONString(ResponseVo.response(ResponseEnum.NO_PERMISSION)), ResponseEnum.NO_PERMISSION.getCode());

    }
}
