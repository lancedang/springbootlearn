package com.lance.spring.springinitstage.config;

import com.lance.spring.springinitstage.MySmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartSingletonConfig {
    @Bean
    public MySmartInitializingSingleton mySmartInitializingSingleton() {
        return new MySmartInitializingSingleton();
    }
}
