package com.lance.apollodemo.spring.annotation;

import com.lance.apollodemo.spring.spi.MyApolloConfigRegistrarHelper;
import com.lance.apollodemo.spring.spi.MyDefaultApolloConfigRegistrarHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class MyApolloConfigImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private final MyApolloConfigRegistrarHelper helper = new MyDefaultApolloConfigRegistrarHelper();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("-------------selfImportBeanDefinitionRegistrar");
        helper.registerBeanDefinition(importingClassMetadata, registry);
    }
}
