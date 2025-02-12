package com.lance.spring.springinitstage.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//模拟循环依赖
//@Scope("prototype")
public class A {

    private B b;

    //@Autowired
    public void setB(B b) {
        this.b = b;
    }
    @Autowired
    public A (B b) {
        this.b = b;
    }
}
