package com.rongji.rjsoft.common.util;



import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 浏览器操作系统工具类
 * @author: JohnYehyo
 * @create: 2021-09-09 14:07:19
 */
public class OsUtils {

    /**
     * 获取浏览器信息
     * @param request 请求
     * @return 浏览器信息
     */
    public static String getBrowserName(HttpServletRequest request){
        UserAgent userAgent = getUserAgent(request);
        return userAgent.getBrowser().toString() + "-" + userAgent.getVersion();
    }

    /**
     * 获取操作系统信息
     * @param request 请求
     * @return 操作系统信息
     */
    public static String getOsName(HttpServletRequest request){
        return getUserAgent(request).getOs().toString();
    }

    public static UserAgent getUserAgent(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        return UserAgentUtil.parse(userAgent);
    }
}
