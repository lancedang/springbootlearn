package com.lance.spring.springinitstage.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class B {

    //场景1：属性依赖注入
    //@Autowired
    private A a;

    //场景2：setter方法注入
    // @Autowired
    public void setA(A a) {
        this.a = a;
    }

    //场景3：构造器注入
    //@Lazy
    @Autowired
    public B (A a) {
        this.a = a;
    }

}
