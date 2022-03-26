package com.lance.eventdemo.controller;

import com.lance.eventdemo.manager.MyEventPublish;
import com.lance.eventdemo.model.MyEvent;
import com.lance.eventdemo.model.MyEventTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private MyEventPublish eventPublish;

    @GetMapping(value = "/create")
    public String createEvent() {
        log.info("createEvent");
        MyEvent event = new MyEvent(this, MyEventTypeEnum.EMAIL_CONFIRM);
        event.setId(111);
        eventPublish.sendEvent(event);

        MyEvent event2 = new MyEvent(this, MyEventTypeEnum.EXPRESS_GET);
        event2.setId(112);
        eventPublish.sendEvent(event2);

        return "create event success";
    }
}
