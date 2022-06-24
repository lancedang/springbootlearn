package com.lance.pdfdemo.service;

import com.lance.pdfdemo.aspect.ProxyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Service1 {

    @ProxyAnnotation
    public void test() {
        log.info("hi");
    }
}
