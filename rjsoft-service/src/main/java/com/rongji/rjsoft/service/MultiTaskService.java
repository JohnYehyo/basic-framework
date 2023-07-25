package com.rongji.rjsoft.service;

import cn.hutool.core.date.StopWatch;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.entity.monitor.SysLoginInfo;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 多线程任务示例
 * @author: JohnYehyo
 * @create: 2023-07-25 09:15:57
 */
@Service
@AllArgsConstructor
public class MultiTaskService {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final ISysLoginInfoService sysLoginInfoService;


    public Object mergeDemo() {
        StopWatch sw = new StopWatch();
        sw.start();
        LambdaQueryWrapper<SysLoginInfo> query1 = new LambdaQueryWrapper<>();
        query1.like(SysLoginInfo::getOs, "OS");
        CompletableFuture<List<SysLoginInfo>> task1 = CompletableFuture.supplyAsync(
                () -> sysLoginInfoService.list(query1), threadPoolTaskExecutor
        ).handle((result, exception) -> {
            if (null != exception) {
                LogUtils.error("查询登录日志失败, 参数:{}", query1);
                return new ArrayList<>();
            }
            return result;
        });


        LambdaQueryWrapper<SysLoginInfo> query2 = new LambdaQueryWrapper<>();
        query2.like(SysLoginInfo::getOs, "10");
        CompletableFuture<List<SysLoginInfo>> task2 = CompletableFuture.supplyAsync(
                () -> sysLoginInfoService.list(query2), threadPoolTaskExecutor
        ).handle((result, exception) -> {
            if (null != exception) {
                LogUtils.error("查询登录日志失败, 参数:{}", query2);
                return new ArrayList<>();
            }
            return result;
        });

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(task1, task2);
        try {
            voidCompletableFuture.get(3, TimeUnit.SECONDS);
        }  catch (Exception e) {
            LogUtils.error("等待所有任务执行失败");
            throw new BusinessException(ResponseEnum.EXCEPTION);
        }

        List<SysLoginInfo> list = Stream.of(task1, task2).map(CompletableFuture::join).flatMap(List::stream).collect(Collectors.toList());
        sw.stop();
        LogUtils.info("总耗时:{}毫秒", sw.getTotalTimeMillis());
        return list;
    }

    public Object syncDemo() {
        StopWatch sw = new StopWatch();
        sw.start();
        LambdaQueryWrapper<SysLoginInfo> query1 = new LambdaQueryWrapper<>();
        query1.like(SysLoginInfo::getOs, "OS");
        List<SysLoginInfo> list1 = sysLoginInfoService.list(query1);
        LambdaQueryWrapper<SysLoginInfo> query2 = new LambdaQueryWrapper<>();
        query2.like(SysLoginInfo::getOs, "10");
        List<SysLoginInfo> list2 = sysLoginInfoService.list(query2);
        List<SysLoginInfo> list = new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);
        sw.stop();
        LogUtils.info("总耗时:{}毫秒", sw.getTotalTimeMillis());
        return list;
    }
}