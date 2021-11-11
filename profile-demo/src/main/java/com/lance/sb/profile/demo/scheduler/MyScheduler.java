package com.lance.sb.profile.demo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MyScheduler implements CommandLineRunner {

    //应用启动后，延迟执行一次
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    //@PostConstruct
    public void test() {
        executorService.schedule(() -> run(), 5, TimeUnit.SECONDS);
    }


    public void run() {
        log.info("hi");
    }

    @Override
    public void run(String... strings) throws Exception {
        executorService.schedule(() -> run(), 5, TimeUnit.SECONDS);
    }
}
