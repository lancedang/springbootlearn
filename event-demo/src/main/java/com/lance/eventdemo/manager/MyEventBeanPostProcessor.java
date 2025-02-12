package com.lance.eventdemo.manager;

import com.lance.eventdemo.model.MyEventTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
@Slf4j
public class MyEventBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private MyEventHandlerManager eventHandlerManager;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        //找到自定义注解的handler并将其缓存到event map
        Class<?> aClass = bean.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            //1）获取所有带有自定义注解的item-handler bean，item代表用户的每种处理event的逻辑单元
            //每种处理单元是以EventTypeEnum划分的
            MyEventHandlerAnnotation annotation = declaredMethod.getAnnotation(MyEventHandlerAnnotation.class);

            if (Objects.isNull(annotation)) {
                continue;
            }
            //2）获取自定义注解上的，EventType
            MyEventTypeEnum eventTypeEnum = annotation.value();

            //3）构造item-handler处理逻辑，
            // 目的是通过反射执行bean 中标记了@MyEventHandlerAnnotation的方法
            // 这里可能不太好理解，event现在是形参，
            // 每个item-handler即@MyEventHandlerAnnotation标记的方法都会携带合约一个参数
            // 这个形参event，啥时候实参话的呢? spring application-listener监听到某个具体Event
            MyEventHandler eventHandler = (event -> {
                try {
                    declaredMethod.invoke(bean, event);
                } catch (Exception e) {
                    log.error("invoke exception, ", e);
                }
            });

            //3）将eventType和eventHandler缓存到map
            eventHandlerManager.registerEvent(eventTypeEnum, eventHandler);
        }

        return bean;
    }
}
