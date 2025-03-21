package com.lance.spring.springinitstage.config;

import org.springframework.stereotype.Component;

@Component
public class DemoObjectEarly {
    public DemoObjectEarly() {
        System.out.println("DemoObjectEarly 构造函数");
    }
    @Override
    public String toString() {
        return "DemoObjectEarly toString";
    }
}
