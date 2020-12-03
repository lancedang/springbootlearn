package com.lance.apollodemo.atimport;

import com.lance.apollodemo.atimport.model.SelfModel1;
import com.lance.apollodemo.atimport.model.SelfModel2;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MyImportBeanDefinationRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableSelfService.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
        String value = attributes.getString("basePackage");

        System.out.println(value);

        //通过宿主注解的field做判断
        if (value.equalsIgnoreCase("com.lance.test")) {
            BeanDefinition beanDefinition1 = BeanDefinitionBuilder.genericBeanDefinition(SelfModel1.class).getBeanDefinition();
            registry.registerBeanDefinition("selfModel1", beanDefinition1);
            BeanDefinition beanDefinition2 = BeanDefinitionBuilder.genericBeanDefinition(SelfModel2.class).getBeanDefinition();
            registry.registerBeanDefinition("selfModel2", beanDefinition2);
        }

    }
}
