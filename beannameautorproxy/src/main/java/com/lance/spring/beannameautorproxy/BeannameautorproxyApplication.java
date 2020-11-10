package com.lance.spring.beannameautorproxy;

import com.lance.spring.beannameautorproxy.beannameautoproxycreator.BeanNameAutoProxyCreatorService;
import com.lance.spring.beannameautorproxy.beannameautoproxycreator.interceptor.BeanNameAutoProxyCreatorServiceInterceptor1;
import com.lance.spring.beannameautorproxy.beannameautoproxycreator.interceptor.NotDelegate2TargetServiceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BeannameautorproxyApplication {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext configurationApplicationContext = SpringApplication.run(BeannameautorproxyApplication.class, args);

        //1）指定target被代理对象
        BeanNameAutoProxyCreatorService beanNameAutoProxyCreatorServiceImpl1 = (BeanNameAutoProxyCreatorService)configurationApplicationContext.getBean("beanNameAutoProxyCreatorServiceImpl1");

        beanNameAutoProxyCreatorServiceImpl1.test("张三");

        //2）不指定target被代理对象，NotDelegate2TargetServiceInterceptor接收请求,但请求不会proceed到target Object
        //使用场景：参考ProxyFactory构造函数注释
        ProxyFactory proxyFactory = new ProxyFactory(BeanNameAutoProxyCreatorService.class, new NotDelegate2TargetServiceInterceptor());
        BeanNameAutoProxyCreatorService proxy = (BeanNameAutoProxyCreatorService) proxyFactory.getProxy();

        proxy.test("not delegate to target object");


    }
}
