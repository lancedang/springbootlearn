package com.lance.spring.springinitstage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MySchedulerRunner implements CommandLineRunner {

    //应用启动后，延迟执行一次
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    //@PostConstruct
    public void test() {
        executorService.schedule(() -> run(), 5, TimeUnit.SECONDS);
    }


    public void run() {
        System.out.println("CommandLineRunner, " +this.getClass().getSimpleName() + ", 延迟5s执行");

    }

    @Override
    public void run(String... strings) throws Exception {
        executorService.schedule(() -> run(), 5, TimeUnit.SECONDS);
    }
}
