package com.lance.spring.springinitstage.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class OrderBeanConfig {
    @Bean
    @Order(100)
    public BeanAByOrder beanAByOrder() {
        return new BeanAByOrder();
    }

    @Bean
    @Order(50)
    public BeanBByOrder beanBByOrder1() {
        return new BeanBByOrder();
    }

    @Bean
    @Order(10)
    public BeanBByOrder beanBByOrder2() {
        return new BeanBByOrder();
    }
}
