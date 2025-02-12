package com.lance.flowdemo.postprocessor;

import com.google.common.collect.Maps;
import com.lance.flowdemo.annotation.FlowAnnotation;
import com.lance.flowdemo.constant.FlowKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {

    public static Map<String, Method> key2MethodMaps = Maps.newConcurrentMap();
    public static Map<String, Object> key2TargetMaps = Maps.newConcurrentMap();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Method[] declaredMethods = bean.getClass().getDeclaredMethods();

        Arrays.stream(declaredMethods).forEach(method -> {
            FlowAnnotation annotation = AnnotationUtils.findAnnotation(method, FlowAnnotation.class);
            if (Objects.nonNull(annotation)) {
                FlowKeyEnum[] keys = annotation.keys();
                Arrays.stream(keys).forEach(key -> {
                    //保存key和method、bean的对应关系
                    key2MethodMaps.put(key.getKey(), method);
                    key2TargetMaps.put(key.getKey(), bean);
                });

                log.info("key2MethodMaps-" + key2MethodMaps + "");
                log.info("key2TargetMaps-" + key2TargetMaps + "");

            }
        });

        log.info("bean={}, bean={}", beanName, bean.getClass().getCanonicalName());

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
