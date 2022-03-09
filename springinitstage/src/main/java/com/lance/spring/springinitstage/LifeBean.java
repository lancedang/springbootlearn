package com.lance.spring.springinitstage;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LifeBean implements InitializingBean, ApplicationContextAware, CommandLineRunner, ApplicationListener<ApplicationReadyEvent> {

    private ApplicationContext applicationContext;

    private LifeDependencyBean1 lifeDependencyBean1;
    private LifeDependencyBean2 lifeDependencyBean2;

    @Autowired
    public LifeBean(LifeDependencyBean2 lifeDependencyBean2) {
        this.lifeDependencyBean2 = lifeDependencyBean2;
        System.out.println("constructor Autowired");
    }

    @Autowired
    public void setLifeDependencyBean1(LifeDependencyBean1 lifeDependencyBean1) {
        System.out.println("setter Autowired");
        this.lifeDependencyBean1 = lifeDependencyBean1;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("ApplicationContextAware");
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("CommandLineRunner");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("ApplicationListener-ApplicationReadyEvent-onApplicationEvent");
    }
}
