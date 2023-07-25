package com.rongji.rjsoft.web.handler;

import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 线程池拒绝策略
 * @author: JohnYehyo
 * @create: 2023-07-25 09:30:13
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        LogUtils.info("线程拒绝策略执行, 线程->{}<-被丢弃", r.toString());
        throw new BusinessException(ResponseEnum.BUSY);
    }
}