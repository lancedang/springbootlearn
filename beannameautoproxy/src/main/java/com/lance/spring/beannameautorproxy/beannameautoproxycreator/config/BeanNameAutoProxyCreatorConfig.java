package com.lance.spring.beannameautorproxy.beannameautoproxycreator.config;


import com.lance.spring.beannameautorproxy.beannameautoproxycreator.interceptor.BeanNameAutoProxyCreatorServiceInterceptor1;
import com.lance.spring.beannameautorproxy.beannameautoproxycreator.interceptor.BeanNameAutoProxyCreatorServiceInterceptor2;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanNameAutoProxyCreatorConfig {

    @Bean(name = "BeanNameAutoProxyCreatorServiceInterceptor1")
    public BeanNameAutoProxyCreatorServiceInterceptor1 getServiceInterceptor() {
        BeanNameAutoProxyCreatorServiceInterceptor1 serviceInterceptor = new BeanNameAutoProxyCreatorServiceInterceptor1();
        return serviceInterceptor;
    }

    @Bean(name = "BeanNameAutoProxyCreatorServiceInterceptor2")
    public BeanNameAutoProxyCreatorServiceInterceptor2 getServiceInterceptor2() {
        BeanNameAutoProxyCreatorServiceInterceptor2 beanNameAutoProxyCreatorServiceInterceptor2 = new BeanNameAutoProxyCreatorServiceInterceptor2();
        return beanNameAutoProxyCreatorServiceInterceptor2;
    }

    @Bean
    public BeanNameAutoProxyCreator getBeanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("beanNameAutoProxyCreatorServiceImpl*");
        beanNameAutoProxyCreator.setInterceptorNames("BeanNameAutoProxyCreatorServiceInterceptor1","BeanNameAutoProxyCreatorServiceInterceptor2");
        return beanNameAutoProxyCreator;
    }

}
