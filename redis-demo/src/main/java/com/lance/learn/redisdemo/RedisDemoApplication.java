package com.lance.learn.redisdemo;

import com.lance.learn.redisdemo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class RedisDemoApplication implements CommandLineRunner {

    @Autowired
    private RedisService redisService;


    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {
        //redis锁的key
        String name = "lance";
        //redis锁的value,分布式锁要用相同的key,value
        String id = UUID.randomUUID().toString();

        log.info("id={}", id);


        Runnable run = () -> {
            try {
                boolean lockOk = redisService.addDistributeLock(name, id, 1000*60);
                if (lockOk) {
                    //模拟同一时刻只能由一个线程执行的方法
                    mutexMethod();
                }
            } catch (InterruptedException e) {
                log.error("error ", e);
            } finally {
                redisService.releaseDistributedLock(name, id);
            }

        };

        //test1(run);
        //test2(run);
        test3(run);

    }

    //分布式互斥方法，模式较为耗时场景 5s
    public void mutexMethod() throws InterruptedException {
        log.info(Thread.currentThread().getName() + " begin deal data ");
        TimeUnit.SECONDS.sleep(5);
        log.info(Thread.currentThread().getName() + " end deal data ");
    }

    public void test1(Runnable run) throws InterruptedException {
        Executors.defaultThreadFactory().newThread(run).start();
        //这里设置的时间很有意思 < 互斥方法5s
        //TimeUnit.SECONDS.sleep(2);
        Executors.defaultThreadFactory().newThread(run).start();
    }

    public void test2(Runnable run) throws InterruptedException {
        Executors.defaultThreadFactory().newThread(run).start();
        //这里设置的时间很有意思 < 互斥方法5s
        TimeUnit.SECONDS.sleep(2);
        Executors.defaultThreadFactory().newThread(run).start();
    }

    public void test3(Runnable run) throws InterruptedException {
        Executors.defaultThreadFactory().newThread(run).start();
        //这里设置的时间很有意思 > 互斥方法5s
        TimeUnit.SECONDS.sleep(6);
        Executors.defaultThreadFactory().newThread(run).start();
    }
}
