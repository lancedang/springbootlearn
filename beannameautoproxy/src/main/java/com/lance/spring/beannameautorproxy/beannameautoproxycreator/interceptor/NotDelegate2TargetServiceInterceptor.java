package com.lance.spring.beannameautorproxy.beannameautoproxycreator.interceptor;

import com.lance.spring.beannameautorproxy.beannameautoproxycreator.BeanNameAutoProxyCreatorService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class NotDelegate2TargetServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object aThis = methodInvocation.getThis();

        log.info("method name= {}", method.getName());

        if (aThis instanceof BeanNameAutoProxyCreatorService) {
            log.info("before interceptor 1");
            methodInvocation.proceed();
            log.info("after interceptor 1");

        }
        return null;
    }
}
