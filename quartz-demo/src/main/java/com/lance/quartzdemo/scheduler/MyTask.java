package com.lance.quartzdemo.scheduler;

import com.lance.quartzdemo.annotation.JobMetaAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MyTask {

    @Autowired
    private MyDao myDao;

    @JobMetaAnnotation(name = "tast1", cron = "0/5 * * * * ? 2022")
    public void task1() {
        log.info("MyTask task1");
    }

    @JobMetaAnnotation(name = "task2", cron = "0/10 * * * * ? 2022", jobParams = "[{\"name\":\"params\",\"type\":\"string\",\"value\":\"0,1,3\"}]")
    public void task2(String params) {
        log.info("MyTask task2 with params = {}", params);
        myDao.getUserList();
    }
}
