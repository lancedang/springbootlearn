package com.lance.pdfdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 *
 */
@Aspect
@Component
@Slf4j
@Order(10)
public class AuditorAspect {

    @Before("ResourcePointCuts.aspect()")
    public void aspect() {
        log.info("before.class={}");
    }


    @Around("ResourcePointCuts.apiController()")
    public Object logAccessAudit(ProceedingJoinPoint apiMethod) throws Throwable {

        log.info("apiMethod.class={}", apiMethod.getClass().getName());

        return apiMethod.proceed();
    }


}
