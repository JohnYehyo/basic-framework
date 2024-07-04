package com.rongji.rjsoft.web.practice3;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @description: 1. 系统启动时启动通知发送和接收的监听
 *               2. 使用自定线程池
 *               3. 执行无返回和有返回值的任务, 有返回值的任务进行模拟合并结果
 * @author: JohnYehyo
 * @create: 2024-07-04 14:54:28
 */
@Component
public class NotificationSystemApplicationRunner implements ApplicationRunner {

    private final BlockingQueue<String> notificationQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> receiptQueue = new LinkedBlockingQueue<>();

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public NotificationSystemApplicationRunner(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void run(ApplicationArguments args) {
        Sender sender = new Sender(notificationQueue, receiptQueue);
        Receiver receiver = new Receiver(receiptQueue);

        //无返回值的异步任务
        for (int i = 0; i < threadPoolTaskExecutor.getCorePoolSize(); i++) {
            CompletableFuture.runAsync(() -> {
                LogUtils.info(RandomUtil.randomString(10));
            }, threadPoolTaskExecutor);
        }

        //有返回值的异步任务
        List<CompletableFuture<String>> list = new ArrayList<>(threadPoolTaskExecutor.getCorePoolSize());
        for (int i = 0; i < threadPoolTaskExecutor.getCorePoolSize(); i++) {
            list.add(CompletableFuture.supplyAsync(() ->
                            RandomUtil.randomString(10)
                    , threadPoolTaskExecutor));
        }
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));
        try {
            completableFuture.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        List<String> collect = list.stream().map(CompletableFuture::join).collect(Collectors.toList());
        LogUtils.info("collect:{}", JSON.toJSONString(collect));

        //启动通知收发线程
        CompletableFuture.runAsync(sender::action, threadPoolTaskExecutor);
        CompletableFuture.runAsync(receiver::action, threadPoolTaskExecutor);

        for (int i = 0; i < 5; i++) {
            try {
                notificationQueue.put("Notification " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        //系统关闭优雅关闭线程(自定义线程池在自定义线程池的配置中设置了关闭)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("Shutdown complete.");
        }));

    }

}