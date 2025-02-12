package com.lance.apollodemo.spring.annotation;

import com.google.common.base.Preconditions;
import com.lance.apollodemo.MyConfig;
import com.lance.apollodemo.MyConfigChangeListener;
import com.lance.apollodemo.internal.MyDefaultConfig;
import com.lance.apollodemo.model.MyConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class MyApolloAnnotationBeanPostProcessor extends MyApolloBeanPostProcessor {
    @Override
    protected void processField(Field field, Object bean) {

        MyApolloConfigAnnotation myApolloConfigAnnotation = AnnotationUtils.findAnnotation(field, MyApolloConfigAnnotation.class);
        if (Objects.isNull(myApolloConfigAnnotation)) {
            return;
        }

        Class<?> fieldType = field.getType();

        //step-01: field必须是MyConfig实现类
        Preconditions.checkArgument(MyConfig.class.isAssignableFrom(fieldType),
                "field annotated by MyApolloConfigAnnotation is not MyConfig class");

        //step-02: 构造属性对象，赋值到属性引用,这里暂时new一个作为演示
        //实际这里必须以namespace构造一个单例的
        String namespace = myApolloConfigAnnotation.value();
        MyConfig myDefaultConfig = MyDefaultConfig.getConfig(namespace);

        ReflectionUtils.makeAccessible(field);
        //设置属性值
        ReflectionUtils.setField(field, bean, myDefaultConfig);

    }

    @Override
    protected void processMethod(Method method, Object bean) {
        MyApolloConfigChangeListenerAnnotation annotation = AnnotationUtils.findAnnotation(method, MyApolloConfigChangeListenerAnnotation.class);
        if (Objects.isNull(annotation)) {
            return;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        Preconditions.checkArgument(parameterTypes.length == 1,
                "config listener method must have one param");

        Preconditions.checkArgument(MyConfigChangeEvent.class.isAssignableFrom(parameterTypes[0]),
                "config listener method param type must be MyConfigChangeEvent");

        String[] namespaces = annotation.value();
        for (String namespace : namespaces) {

            MyConfig myConfig = MyDefaultConfig.getConfig(namespace);

            //step-01: 构建ConfigChangeListener
            MyConfigChangeListener configChangeListener = configChangeEvent -> {
                try {
                    method.invoke(bean, configChangeEvent);
                } catch (Exception e) {
                    log.error("change listener onChange fail, ", e);
                }
            };

            //step-02: 将configChangeListener添加到config上
            myConfig.addChangeListener(configChangeListener);
        }
    }

    @Override
    public String toString() {
        return "MyApolloAnnotationBeanPostProcessor{}";
    }
}
