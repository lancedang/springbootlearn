package com.lance.apollodemo.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {MyApolloConfigImportBeanDefinitionRegistrar.class})
public @interface MyEnableApolloConfig {
    String[] value() default {"application"};
}
