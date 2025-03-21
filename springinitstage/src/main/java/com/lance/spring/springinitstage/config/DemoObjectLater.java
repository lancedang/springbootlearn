package com.lance.spring.springinitstage.config;

import org.springframework.stereotype.Component;

@Component
public class DemoObjectLater {

    public DemoObjectLater() {
        System.out.println("DemoObjectLater 构造函数");
    }

    @Override
    public String toString() {
        return "DemoObjectLater toString";
    }
}
