package com.lance.eventdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RestController()
@RequestMapping("/executor/test")
public class RunService {
    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @PostConstruct
    public void init() {
        scheduler.scheduleWithFixedDelay(this::work, 2000 * 1);
    }

    public void work() {
        log.info("work begin");
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("work end");
    }

    @GetMapping("/close")
    public String close() {
        log.info("reecive close command");
        scheduler.shutdown();
        //scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return "success";
    }



}
