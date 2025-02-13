package com.lance.apollodemo.spring.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public abstract class MyApolloBeanPostProcessor
        implements BeanPostProcessor, PriorityOrdered {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<Field> allFields = findAllFields(bean.getClass());
        List<Method> allMethods = findAllMethods(bean.getClass());
        for (Field field : allFields) {
            processField(field, bean);
        }

        for (Method method : allMethods) {
            processMethod(method, bean);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected abstract void processField(Field field, Object bean);

    protected abstract void processMethod(Method method, Object bean);

    public List<Field> findAllFields(Class clazz) {
        List<Field> fieldList = new LinkedList<>();
        //在这个class的所有field上做啥事（lambda)
        ReflectionUtils.doWithFields(clazz, (field) -> {
            fieldList.add(field);
        });

        return fieldList;
    }

    public List<Method> findAllMethods(Class clazz) {
        List<Method> methodList = new LinkedList<>();
        //故意写成2种方式，上面field是lambda的写法，下面是method reference写法
        ReflectionUtils.doWithMethods(clazz, methodList::add);
        return methodList;
    }

    @Override
    public int getOrder() {
        //设置为最低优先级，数字越小优先级越大
        return Ordered.LOWEST_PRECEDENCE;
    }
}
