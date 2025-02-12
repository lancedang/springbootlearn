package com.lance.flowdemo.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBeanFactoryProcessor2 implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition myTestBean = beanFactory.getBeanDefinition("myTestBean");
        myTestBean.getPropertyValues().add("name", "qiankai");
        myTestBean.getPropertyValues().add("addr", "shanghai");

    }
}
