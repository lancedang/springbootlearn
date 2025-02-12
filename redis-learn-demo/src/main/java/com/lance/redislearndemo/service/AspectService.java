package com.lance.redislearndemo.service;

import com.lance.redislearndemo.aspect.MyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AspectService {
    @MyAnnotation(count = 10)
    public void test1() {
        log.info("test1");
    }

    //@MyAnnotation(count = 10)
    public void test2() {
        log.info("test2");
    }
}
