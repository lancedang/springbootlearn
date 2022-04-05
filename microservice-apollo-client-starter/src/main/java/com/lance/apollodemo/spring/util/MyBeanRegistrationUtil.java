package com.lance.apollodemo.spring.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Map;
import java.util.Objects;

public class MyBeanRegistrationUtil {

    public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry beanDefinitionRegistry,
                                                            String beanName,
                                                            Class<?> beanClass) {
        return registerBeanDefinitionIfNotExists(beanDefinitionRegistry, beanName, beanClass, null);
    }

    public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry beanDefinitionRegistry,
                                                            String beanName,
                                                            Class<?> beanClass,
                                                            Map<String, Object> extraPropertyValues) {

        //判断-01：同名bean
        boolean exists = beanDefinitionRegistry.containsBeanDefinition(beanName);

        if (exists) {
            return false;
        }

        String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);

            //判断-02：相同类型名
            if (Objects.equals(beanDefinition.getBeanClassName(), beanClass.getName())) {
                return false;
            }

        }

        //step-01: 构造beanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();

        //step-02: beanDefinition添加属性
        if (extraPropertyValues != null) {
            for (Map.Entry<String, Object> entry : extraPropertyValues.entrySet()) {
                beanDefinition.getPropertyValues().add(entry.getKey(), entry.getValue());
            }
        }


        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        return true;
    }

}
