package com.lance.spring.beannameautorproxy.beannameautoproxycreator.interceptor;

import com.lance.spring.beannameautorproxy.beannameautoproxycreator.BeanNameAutoProxyCreatorService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class BeanNameAutoProxyCreatorServiceInterceptor2 implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object aThis = methodInvocation.getThis();

        if (aThis instanceof BeanNameAutoProxyCreatorService) {
            log.info("before inteceptor 2");
            methodInvocation.proceed();
            log.info("after inteceptor 2");
        }

        return null;
    }
}
