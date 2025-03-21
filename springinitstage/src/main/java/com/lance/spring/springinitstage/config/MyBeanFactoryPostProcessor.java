package com.lance.spring.springinitstage.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory
            (ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Object bean = beanFactory.getBean(DemoObjectEarly.class);
        System.out.println(bean);
    }
}
