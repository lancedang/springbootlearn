package com.lance.apollodemo.spring.spi;

import com.lance.apollodemo.spring.annotation.MyApolloAnnotationBeanPostProcessor;
import com.lance.apollodemo.spring.annotation.MyEnableApolloConfig;
import com.lance.apollodemo.spring.util.MyBeanRegistrationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

@Slf4j
public class MyDefaultApolloConfigRegistrarHelper implements MyApolloConfigRegistrarHelper {
    @Override
    public void registerBeanDefinition(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        //通过annotationMetadata传递
        Map<String, Object> annotationAttributeMap = importingClassMetadata.getAnnotationAttributes(MyEnableApolloConfig.class.getName());

        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributeMap);

        //获取注解上的属性，可以以各种形式获取
        String[] values = annotationAttributes.getStringArray("value");

        log.info("----------values={}", values);

        //注册几个自定义bean
        MyBeanRegistrationUtil.registerBeanDefinitionIfNotExists(
                beanDefinitionRegistry,
                MyApolloAnnotationBeanPostProcessor.class.getName(),
                MyApolloAnnotationBeanPostProcessor.class);


    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
