package com.lance.spring.springinitstage.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

//@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class BeanAByOrder implements InitializingBean {
    public BeanAByOrder() {
        System.out.println("BeanA ByOrder 构造函数");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BeanA ByOrder InitializingBean.afterPropertiesSet()");
    }

    @Override
    public String toString() {
        return "Bean A";
    }
}
