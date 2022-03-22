package com.lance.flowdemo.config;

import com.lance.flowdemo.entity.MyTestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean(name = "myTestBean")
    public MyTestBean testBan() {
        MyTestBean myTestBan = new MyTestBean();
        myTestBan.setName("张三");
        return myTestBan;
    }

}
