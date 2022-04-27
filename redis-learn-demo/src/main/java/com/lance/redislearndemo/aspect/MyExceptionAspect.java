package com.lance.redislearndemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class MyExceptionAspect {

    @Pointcut("@annotation(com.lance.redislearndemo.aspect.MyExceptionAnnotation) || @within(com.lance.redislearndemo.aspect.MyExceptionAnnotation)")
    public void pointCut() {

    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.error("afterThrowing, ", throwable);
        //throw new RuntimeException("in after throwoing");
    }

    @Around("@annotation(com.lance.redislearndemo.aspect.MyExceptionAnnotation) || @within(com.lance.redislearndemo.aspect.MyExceptionAnnotation)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            log.error("in around catch", e);
            throw e;

        } finally {
            log.error("in around finally");
        }
    }


}
