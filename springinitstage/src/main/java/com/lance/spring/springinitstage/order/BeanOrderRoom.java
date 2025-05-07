package com.lance.spring.springinitstage.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeanOrderRoom {
    @Autowired
    public List<BeanBByOrder> beanBByOrders;

    public void show() {
        for (BeanBByOrder item : beanBByOrders) {
            System.out.println(item);
        }
    }

}
