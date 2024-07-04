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
 * 2. 使用自定线程池
 * 3. 执行无返回和有返回值的任务, 有返回值的任务进行模拟合并结果
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
        for (int i = 0; i < threadPoolTaskExecutor.getMaxPoolSize(); i++) {
            CompletableFuture.runAsync(() -> {
                LogUtils.info(RandomUtil.randomString(10));
            }, threadPoolTaskExecutor);
        }

        /*
            体会以下两种有返回值异步任务对结果处理的区别:
            第一种会双阻塞(第一次get阻塞是为了给全部任务线程限制总时间, 第二次join是为了获取结果)
            第二种不会双阻塞(只有get), 但是可能因为线程池的设置(线程池被耗尽, 实测调大核心线程的确
            可以减少这种情况)导致最后结果比总线程少,本例中如果循环是100collect2应该有100个结果但
            是collect2的size经常小于100:
            每个future一完成时就立即加入到collect2中，无论完成是否成功。然而，由于这些操作是异步的，
            并且外部循环在等待之前的任务完成之前就继续提交新的任务，如果主线程在所有future都有机会将
            它们的结果加入到collect2之前就完成了它的执行，那么collect2可能会收到少于100个条目
         */

        //有返回值的异步任务
        List<CompletableFuture<String>> list = new ArrayList<>(threadPoolTaskExecutor.getMaxPoolSize());
        for (int i = 0; i < threadPoolTaskExecutor.getMaxPoolSize(); i++) {
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
        LogUtils.info("collect:{}, 长度:{}", JSON.toJSONString(collect), collect.size());

        //有返回值的异步任务2
        List<CompletableFuture<String>> list2 = new ArrayList<>(threadPoolTaskExecutor.getMaxPoolSize());
        List<String> collect2 = new ArrayList<>();
        for (int i = 0; i < threadPoolTaskExecutor.getMaxPoolSize(); i++) {
            list2.add(CompletableFuture.supplyAsync(() ->
                            RandomUtil.randomString(10)
                    , threadPoolTaskExecutor).handle((s, throwable) -> {
                if (throwable != null) {
                    LogUtils.error("异常:{}", throwable.getMessage());
                    return null;
                } else {
                    collect2.add(s);
                    return s;
                }
            }));
        }
        CompletableFuture<Void> completableFuture2 = CompletableFuture.allOf(list2.toArray(new CompletableFuture[0]));
        try {
            completableFuture2.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        LogUtils.info("collect2:{}, 长度:{}", JSON.toJSONString(collect2), collect2.size());

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