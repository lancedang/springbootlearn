package com.lance.spring.springinitstage.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BeanBByOrder implements InitializingBean {
    public BeanBByOrder() {
        System.out.println("BeanB ByOrder");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BeanB ByOrder InitializingBean.afterPropertiesSet()");
    }

    @Override
    public String toString() {
        return "Bean B";
    }
}
