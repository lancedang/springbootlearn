package com.lance.redislearndemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class RedisLearnDemoApplication implements CommandLineRunner {

    @Autowired
    private Redis redis;



    public static void main(String[] args) {
        SpringApplication.run(RedisLearnDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redis.setValueWithTime("test1", "1", 10, TimeUnit.SECONDS);

        log.info("expire=" + redis.getExpireTime("test1", TimeUnit.SECONDS));
        TimeUnit.SECONDS.sleep(9);
        log.info("expire1=" + redis.getExpireTime("test1", TimeUnit.SECONDS));

        redis.expire("test1", 10, TimeUnit.SECONDS);
        log.info("expire2=" + redis.getExpireTime("test1", TimeUnit.SECONDS));


    }
}
