package com.lance.retrydemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

@SpringBootApplication
@Slf4j
public class RetryDemoApplication implements CommandLineRunner {

    @Autowired
    private RetryTemplate retryTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RetryDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        RetryCallback<Boolean, Exception> retryCallback = (retryContext) -> retryMethod();

        try {
            retryTemplate.execute(retryCallback);
        } catch (Exception e) {
            log.error("Error, ", e);
        }

    }

    public boolean retryMethod() {
        log.info(Thread.currentThread().getName() + " execute retryMethod");
        if (true) {
            throw new RuntimeException("qiankai");
        }
        return false;
    }
}
