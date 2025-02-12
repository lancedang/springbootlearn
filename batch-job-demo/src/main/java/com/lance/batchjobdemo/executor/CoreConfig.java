package com.lance.batchjobdemo.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class CoreConfig {

    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor;
        threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setKeepAliveSeconds(1000);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(10);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix("my-task-");
        return threadPoolTaskExecutor;
    }
}
