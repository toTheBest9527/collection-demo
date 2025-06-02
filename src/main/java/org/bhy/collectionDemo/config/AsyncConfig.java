package org.bhy.collectionDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 20:21
 * @PackageName: org.bhy.collectionDemo.config
 * @ClassName: AsyncConfig
 * @Description: 使用SpringBoot提供的异步配置，自定义线程池
 * @Version 1.0
 */
@Configuration
public class AsyncConfig {

    //定义线程池
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(8);
        //设置最大线程数
        executor.setMaxPoolSize(8);
        //设置队列容量
        executor.setQueueCapacity(0);
        //设置线程池前缀
        executor.setThreadNamePrefix("Async-");
        //设置拒绝策略，阻塞队列满直接抛弃任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //初始化线程池
        executor.initialize();
        return executor;
    }
}
