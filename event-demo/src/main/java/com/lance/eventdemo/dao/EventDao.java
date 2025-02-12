package com.lance.eventdemo.dao;

import com.lance.eventdemo.model.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventDao {

    public void saveEvent(MyEvent event) {
        log.info("dao save event");
    }

}
