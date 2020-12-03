package com.lance.apollodemo.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lance.apollodemo.ApolloChangeHandler;
import com.lance.apollodemo.annotation.ApolloConfig;
import com.lance.apollodemo.apollo.ApolloConfigService;
import com.lance.apollodemo.apollo.server.MockApolloServer;
import org.omg.CosNaming.NamingContextPackage.InvalidNameHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class ApolloBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        processFields(declaredFields, bean);

        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        processMethods(declaredMethods, bean);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    private void processFields(Field[] fields, Object bean) {
        for (Field field : fields) {
            ApolloConfig annotation = field.getAnnotation(ApolloConfig.class);
            if (annotation == null) {
                continue;
            }

            //1.根据注解参数获取变量真正值
            String value = annotation.value();
            Map<String, String> map = MockApolloServer.getMap(value);

            ApolloConfigService apolloConfigService = new ApolloConfigService().build(map);

            ReflectionUtils.makeAccessible(field);
            //2.设置变量值（有点依赖注入的思想）
            ReflectionUtils.setField(field, bean, apolloConfigService);
        }
    }


    private void processMethods(Method[] methods, Object bean) {
        for (Method method : methods) {
            ApolloChangeHandler annotation = method.getAnnotation(ApolloChangeHandler.class);
            if (annotation == null) {
                continue;
            }
            //注册回调函数，服务端->回调函数->回调函数触发client重新拉取server配置的动作

        }
    }
}
