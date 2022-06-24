package com.lance.pdfdemo.controller;

import com.lance.pdfdemo.NullTestService;
import com.lance.pdfdemo.service.Service1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/null/portal/portrait/pdf")
@Slf4j
public class NullTestController {

    @Autowired
    private NullTestService nullTestService;

    @Autowired
    private Service1 service1;

    @Autowired
    private Controller2 controller2;

    @GetMapping
    public String test(@RequestParam("name") String name) {
        nullTestService.test();
        service1.test();
        log.info("service1 class={}", service1.getClass());
        log.info("controller2 class={}", controller2.getClass());
        return "success";
    }

    @PostMapping
    private String test2() {
        return "";
    }
}

