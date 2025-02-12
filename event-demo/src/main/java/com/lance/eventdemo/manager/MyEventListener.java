package com.lance.eventdemo.manager;

import com.lance.eventdemo.model.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener implements ApplicationListener<MyEvent> {

    @Autowired
    private MyEventHandlerManager eventHandlerManager;

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        eventHandlerManager.handle(myEvent);
    }
}
