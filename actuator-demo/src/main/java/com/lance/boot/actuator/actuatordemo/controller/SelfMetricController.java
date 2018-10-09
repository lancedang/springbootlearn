package com.lance.boot.actuator.actuatordemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelfMetricController {

    @Autowired
    private CounterService counterService;

    @GetMapping(value = "/count")
    public String counterExample() {
        String name = this.getClass().getSimpleName() + ".method.count";
        counterService.increment(name);
        return "hello";
    }

}
