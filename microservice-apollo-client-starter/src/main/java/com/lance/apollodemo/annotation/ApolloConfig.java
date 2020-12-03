package com.lance.apollodemo.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApolloConfig {
    String value() default "application";
}
