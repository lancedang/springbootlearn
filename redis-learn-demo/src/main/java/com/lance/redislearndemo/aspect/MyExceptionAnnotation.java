package com.lance.redislearndemo.aspect;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface MyExceptionAnnotation {
}
