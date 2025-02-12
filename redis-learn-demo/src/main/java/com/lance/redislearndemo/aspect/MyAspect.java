package com.lance.redislearndemo.aspect;

import com.lance.redislearndemo.Redis;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class MyAspect {

    public static final String KEY_PREFIX = "redis-learn:";

    @Autowired
    private Redis redis;

    @Pointcut("@annotation(com.lance.redislearndemo.aspect.MyAnnotation)")
    public void cut() {

    }

    //@Before("cut()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();


        MethodSignature methodSignature = (MethodSignature) signature;
        //获取当前执行的方法
        String name = methodSignature.getName();
        log.info("current methodSignature.getName={}", name);

        //获取目的方法的返回类型
        Object target = joinPoint.getTarget();
        //可以找到Method对象
        Method method = target.getClass().getMethod(name);

        //通过method可以找到注解
        Annotation annotation = method.getAnnotation(MyAnnotation.class);
        log.info("annotation={}", annotation);
        log.info("current method={}", method);
    }

    //添加annotation注解参数，方便直接获取注解信息，比如count
    @Before("cut() && @annotation(myAnnotation)")
    public void before2(JoinPoint joinPoint, MyAnnotation myAnnotation) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();

        String key = Redis.KEY_PREFIX + remoteAddr + ":" + requestURI;

        long value = redis.incrementValue(key);
        log.info("value={}", value);

        if (value == 1) {
            //log.info("value=1");
            redis.expire(key, myAnnotation.span(), TimeUnit.SECONDS);
        }

        if (value > myAnnotation.count()) {
            log.error("exceed max visit number, current={}, max={}", value, myAnnotation.span());
            throw new RuntimeException("exceed max visit number");
        }

    }
}
