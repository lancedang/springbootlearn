package com.lance.apollodemo.spring.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyApolloConfigChangeListenerAnnotation {
    //这里namespace是数组，标志可以监听多个config
    String[] value() default {"application"};
}
