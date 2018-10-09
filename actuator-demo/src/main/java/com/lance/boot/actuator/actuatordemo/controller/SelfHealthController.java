package com.lance.boot.actuator.actuatordemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Component
public class SelfHealthController implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }
}
