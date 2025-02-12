package com.lance.eventdemo.manager;

import com.lance.eventdemo.model.MyEvent;

//这个是统一收口的handler对比item-handler
public interface MyEventHandler {
    void handle(MyEvent event);
}
