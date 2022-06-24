package com.lance.pdfdemo.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 *
 */
public class ResourcePointCuts {

    @Pointcut("execution(public * com.lance.pdfdemo.controller..*.*(..))")
    public void apiController() {
    }

    @Pointcut("@annotation(com.lance.pdfdemo.aspect.ProxyAnnotation)")
    public void aspect() {

    }

}
