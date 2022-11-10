package com.hrdate.oj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 线程池自动配置类
 *  * @author huangrendi
 *  * @since 2022-11-08
 */
@Slf4j
@EnableAsync
@Configuration
public class ThreadPoolAutoConfiguration {

    /**
     * 定时任务公用线程池
     *
     * @return ThreadPoolTaskScheduler
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(4);
        taskScheduler.setThreadNamePrefix("My-OlineJudge-ThreadPoolTaskScheduler-");
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 调度器 shutdown() 方法被调用时等待当前被调度的任务完成
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 最大等待时长，单位：秒
        taskScheduler.setAwaitTerminationSeconds(180);
        return taskScheduler;
    }
}

