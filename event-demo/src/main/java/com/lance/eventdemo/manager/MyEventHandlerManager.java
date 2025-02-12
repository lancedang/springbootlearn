package com.lance.eventdemo.manager;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lance.eventdemo.model.MyEvent;
import com.lance.eventdemo.model.MyEventTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class MyEventHandlerManager {

    private ExecutorService executorService;

    private static Map<MyEventTypeEnum, MyEventHandler> eventHandlerMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){

        ThreadFactory build = new ThreadFactoryBuilder().setNameFormat("lance-thread-%d").build();
        executorService = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), build);

    }

    //首先，保存event和handler的对应关系
    public void registerEvent(MyEventTypeEnum eventTypeEnum, MyEventHandler eventHandler) {
        eventHandlerMap.put(eventTypeEnum, eventHandler);
    }

    public void handle(MyEvent myEvent) {
        log.info("handle myEvent={}", myEvent);

        MyEventTypeEnum eventTypeEnum = myEvent.getEventTypeEnum();

        //获取event对应的item-handler
        MyEventHandler myEventHandler = eventHandlerMap.get(eventTypeEnum);

        //这里的Event是beaPostProcessor的反射方法实参method.invoke(bean, args)中的args
        //用线程池异步多线程并行执行
        executorService.submit(() -> myEventHandler.handle(myEvent));

    }


}
