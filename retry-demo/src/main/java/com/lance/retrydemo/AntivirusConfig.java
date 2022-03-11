package com.lance.retrydemo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.ExpressionRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAsync
public class AntivirusConfig {

    @Bean(name = "antivirusScanExecutor")
    public TaskExecutor antivirusScanExecutor() {

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(4);
        return executor;
    }

    @Bean(name = "antivirusRetryTemplate")
    public RetryTemplate antivirusRetryTemplate() {

        RetryTemplate retryTemplate = new RetryTemplate();
        //AntivirusProperties.RetryConfig retryConfig = properties.getRetry();
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(RuntimeException.class, true);
        RetryPolicy policy = new ExpressionRetryPolicy(3, retryableExceptions, true, "#{message.contains('Error while communicating with the server')}");

        retryTemplate.setRetryPolicy(policy);
        //AntivirusProperties.BackOffPolicyConfig backOffPolicyConfig = retryConfig.getBackoff();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(3);
        backOffPolicy.setMaxInterval(3);
        backOffPolicy.setMultiplier(3);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}