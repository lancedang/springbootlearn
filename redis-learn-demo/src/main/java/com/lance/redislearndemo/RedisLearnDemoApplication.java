package com.lance.redislearndemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

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

        String testKey = "scf-risk";
        Long size = redis.set().size(testKey);
        log.info("指标集合中共有{}个key", size);
        //ScanOptions scanOptions = ScanOptions.scanOptions().count(size).match("out_service_*_request_total@@*").build();

        //queueName = queueName.contains("/") ? queueName.split("/")[1] : queueName;

        ScanOptions scanOptions = ScanOptions.scanOptions().count(size).match("*subscribe*").build();
        Cursor<String> keys = redis.set().scan(testKey, scanOptions);
        if (keys != null) {
            int count = 0;
            while (keys.hasNext()) {
                //需要判断key是否还存在
                String key = keys.next();
                log.info("metric key={}", key);
            }
            log.info("共推送{}条指标数据", count);
        }
    }
}
