package com.lance.quartzdemo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//模拟：MyDao被MyTask autowired,看看MyTask能否成功注入myDao bean
@Component
@Slf4j
public class MyDao {
    public void getUserList() {
        log.info("user list is ....");
    }
}
