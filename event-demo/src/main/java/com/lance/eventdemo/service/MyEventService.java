package com.lance.eventdemo.service;

import com.lance.eventdemo.manager.MyEventHandlerAnnotation;
import com.lance.eventdemo.model.MyEvent;
import com.lance.eventdemo.model.MyEventTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyEventService {

    //event item-handler处理逻辑
    @MyEventHandlerAnnotation(value = MyEventTypeEnum.EMAIL_CONFIRM)
    public void dealEmailEvent(MyEvent event) {
        log.info("deal email event eventId={}", event.getId());
    }

    @MyEventHandlerAnnotation(value = MyEventTypeEnum.EXPRESS_GET)
    public void dealExpressEvent2(MyEvent event) {
        log.info("deal express event eventId={}", event.getId());
    }
}
