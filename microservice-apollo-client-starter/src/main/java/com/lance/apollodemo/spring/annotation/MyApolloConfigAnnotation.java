package com.lance.apollodemo.spring.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyApolloConfigAnnotation {
    //标志这个config是哪个namespace的config，config是以namespace作为区分的，好比员工的工号
    String value() default "application";
}
