package com.lance.spring.springinitstage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (s.equalsIgnoreCase("lifeBean")) {
            System.out.println("BeanPostProcessor before initialization");
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (s.equalsIgnoreCase("lifeBean")) {
            System.out.println("BeanPostProcessor after initialization");
        }
        return o;
    }
}
