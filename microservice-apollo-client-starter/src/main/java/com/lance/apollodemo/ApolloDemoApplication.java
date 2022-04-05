package com.lance.apollodemo;

import com.google.common.collect.Lists;
import com.lance.apollodemo.enums.MyConfigSourceType;
import com.lance.apollodemo.enums.MyPropertyChangeType;
import com.lance.apollodemo.internal.MyDefaultConfig;
import com.lance.apollodemo.model.MyConfigChange;
import com.lance.apollodemo.model.MyConfigChangeEvent;
import com.lance.apollodemo.service.UserService;
import com.lance.apollodemo.spring.annotation.MyApolloAnnotationBeanPostProcessor;
import com.lance.apollodemo.spring.annotation.MyApolloConfigImportBeanDefinitionRegistrar;
import com.lance.apollodemo.spring.annotation.MyEnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MyEnableApolloConfig
@Slf4j
public class ApolloDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(ApolloDemoApplication.class, args);

        UserService userService = applicationContext.getBean(UserService.class);
        //验证-1：验证config field已经被赋值
        MyConfig apolloConfig = userService.getApolloConfig();

        //验证-2：验证bean已经被bean
        MyApolloAnnotationBeanPostProcessor selfBeanPostProcessor = applicationContext.getBean(MyApolloAnnotationBeanPostProcessor.class);

        //MyApolloConfigImportBeanDefinitionRegistrar selfBeanRegistrar = applicationContext.getBean(MyApolloConfigImportBeanDefinitionRegistrar.class);

        boolean exists = applicationContext.containsBean(MyApolloConfigImportBeanDefinitionRegistrar.class.getName());

        log.info("selfBeanPostProcessor={}", selfBeanPostProcessor);
        log.info("selfBeanRegistrar={}", exists);
        log.info("apolloConfig={}", apolloConfig);

        MyDefaultConfig myDefaultConfig = (MyDefaultConfig) apolloConfig;

        String namespace = myDefaultConfig.getNamespace();

        //触发属性变化-01：构造event
        MyConfigChangeEvent event = new MyConfigChangeEvent();
        event.setNamespace(namespace);

        MyConfigChange myConfigChange = new MyConfigChange();
        myConfigChange.setKey("key1");
        myConfigChange.setChangeType(MyPropertyChangeType.ADDED);
        myConfigChange.setNewValue("newValue1");
        myConfigChange.setOldValue("oldValue1");
        myConfigChange.setNamespace(namespace);

        ArrayList<MyConfigChange> objects = Lists.newArrayList();
        objects.add(myConfigChange);

        Map<String, List<MyConfigChange>> map = new HashMap<>();
        map.put(namespace, objects);

        event.setConfigChangeMap(map);

        //触发属性变化-01：触发event
        log.info("start fire config change event");
        myDefaultConfig.fireConfigChange(event);


    }

}
