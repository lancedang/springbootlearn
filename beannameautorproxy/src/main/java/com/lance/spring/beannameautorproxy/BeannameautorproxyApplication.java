package com.lance.spring.beannameautorproxy;

import com.lance.spring.beannameautorproxy.beannameautoproxycreator.BeanNameAutoProxyCreatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BeannameautorproxyApplication {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext configurationApplicationContext = SpringApplication.run(BeannameautorproxyApplication.class, args);

        BeanNameAutoProxyCreatorService beanNameAutoProxyCreatorServiceImpl1 = (BeanNameAutoProxyCreatorService)configurationApplicationContext.getBean("beanNameAutoProxyCreatorServiceImpl1");

        beanNameAutoProxyCreatorServiceImpl1.test("张三");


    }
}
