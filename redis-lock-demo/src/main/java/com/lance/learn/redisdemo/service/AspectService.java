package com.lance.learn.redisdemo.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AspectService {

    public void test1() {
        log.info("test1");
    }

    //@MyAnnotation(count = 10)
    public void test2() {
        log.info("test2");
    }
}
