package com.lance.flowdemo.annotation;

import com.lance.flowdemo.constant.FlowKeyEnum;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface FlowAnnotation {
    FlowKeyEnum[] keys() default {};
}
