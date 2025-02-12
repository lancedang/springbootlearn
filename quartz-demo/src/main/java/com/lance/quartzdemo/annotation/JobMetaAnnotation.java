package com.lance.quartzdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 修饰方法的注解：本demo是基于方法可以作为调度对象（调度单位）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JobMetaAnnotation {

    //job名字
    String name() default "";

    //cron表达式：quartz 调度频次等
    String cron() default "";

    //传递给job方法的参数：方法参数抽出来而已
    String jobParams() default "";
}
