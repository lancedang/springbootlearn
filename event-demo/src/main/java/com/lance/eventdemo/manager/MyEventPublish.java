package com.lance.eventdemo.manager;

import com.lance.eventdemo.model.MyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyEventPublish {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void sendEvent(MyEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
