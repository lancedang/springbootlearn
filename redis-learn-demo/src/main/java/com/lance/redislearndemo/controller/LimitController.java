package com.lance.redislearndemo.controller;

import com.lance.redislearndemo.aspect.MyAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class LimitController {

    @MyAnnotation(count = 3, span = 10)
    @GetMapping(value = "/test")
    public String test() {
        return "success";
    }

}
