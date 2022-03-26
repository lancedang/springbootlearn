package com.lance.eventdemo.model;

import org.springframework.context.ApplicationEvent;

//@Data
public class MyEvent extends ApplicationEvent {

    private int id;
    private MyEventTypeEnum eventTypeEnum;

    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, MyEventTypeEnum eventTypeEnum) {
        super(source);
        this.eventTypeEnum = eventTypeEnum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyEventTypeEnum getEventTypeEnum() {
        return eventTypeEnum;
    }

    public void setEventTypeEnum(MyEventTypeEnum eventTypeEnum) {
        this.eventTypeEnum = eventTypeEnum;
    }
}
