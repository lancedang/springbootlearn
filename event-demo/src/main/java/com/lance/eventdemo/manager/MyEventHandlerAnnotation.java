package com.lance.eventdemo.manager;

import com.lance.eventdemo.model.MyEventTypeEnum;

import java.lang.annotation.*;

//用于标记item-handler, 每个处理event的逻辑单元
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyEventHandlerAnnotation {
    //每种event一种处理逻辑
    MyEventTypeEnum value();
}
