package com.rongji.rjsoft.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 日志工具
 * @author: JohnYehyo
 * @create: 2021-04-26 14:38:06
 */
public class LogUtils {

    private static final Logger INFO = LoggerFactory.getLogger("info");
    private static final Logger WARN = LoggerFactory.getLogger("warn");
    private static final Logger ERROR = LoggerFactory.getLogger("error");

    /**
     * INFO日志
     * @param msg 信息
     * @param objs 数据
     */
    public static void info(String msg, Object... objs) {
        INFO.info(msg, objs);
    }

    /**
     * WARN日志
     * @param msg 信息
     * @param objs 数据
     */
    public static void warn(String msg, Object... objs) {
        WARN.warn(msg, objs);
    }

    /**
     * 错误日志
     * @param msg 信息
     * @param t Throwable
     */
    public static void error(String msg, Throwable t) {
        ERROR.error(msg, t);
    }

    /**
     * 错误日志
     * @param msg 信息
     */
    public static void error(String msg) {
        ERROR.error(msg);
    }

    /**
     * 错误日志
     * @param msg 信息
     * @param objs 数据
     */
    public static void error(String msg, Object... objs) {
        ERROR.error(msg, objs);
    }
}
