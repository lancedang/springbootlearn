package com.lance.apollodemo.spring.spi;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;

public interface MyApolloConfigRegistrarHelper extends Ordered {
    void registerBeanDefinition(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry);
}
