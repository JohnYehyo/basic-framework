package com.rongji.rjsoft.web.config;

import com.rongji.rjsoft.web.handler.MyRejectedExecutionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;

/**
 * @description: 线程池配置
 * @author: JohnYehyo
 * @create: 2023-07-25 09:28:40
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程池大小
     */
    private int corePoolSize = 5;

    /**
     * 最大可创建的线程数
     */
    private int maxPoolSize = 100;

    /**
     * 队列最大长度
     */
    private int queueCapacity = 20;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds = 60;

    private ThreadPoolTaskExecutor executor;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new MyRejectedExecutionHandler());
        executor.setThreadNamePrefix("JohnYehyo-pool-thread-");
        return executor;
    }

    @PreDestroy
    public void preDestroy() {
        if (null != executor) {
            System.out.println("关闭线程池...");
            executor.shutdown();
        }
    }

}