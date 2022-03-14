package com.lance.batchjobdemo;

import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;

public class RetryTemplateFactory {

    public static RetryTemplate getTemplate(int retryTimes, int sleepBeforeTry, Class<? extends Throwable> exceptionClass) {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(retryTimes,
                Collections.<Class<? extends Throwable>, Boolean>singletonMap(exceptionClass, true));
        fixedBackOffPolicy.setBackOffPeriod(sleepBeforeTry);// sleep for sleepBeforeTry milliseconds
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        return retryTemplate;
    }
}