package com.lance.flowdemo.controller;

import com.google.gson.Gson;
import com.lance.flowdemo.entity.MyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class MyUnfiedAdvice implements ResponseBodyAdvice {

    public static Gson gson = new Gson();

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("old Object=" + o + "");

        //过滤掉ExceptionHandler注解的方法，ExceptionHandler注解的方法只有异常出现时才会执行
        //我们已经在其中构造了fail Response，故过滤掉之，
        // 其余情况按统一Response返回
        Annotation exceptAnnotation = methodParameter.getMethodAnnotation(ExceptionHandler.class);

        if (Objects.nonNull(exceptAnnotation)) {
            return o;
        }

        MyResponse response = new MyResponse();
        response.setCode(0);
        response.setDesc("success");
        response.setData(o);

        log.info("new Response={}", response);

        return response;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, HttpServletResponse response1, Throwable e) {
        MyResponse response = new MyResponse();
        response.setCode(1);
        response.setDesc("fail");
        //response.setData(o);
        return response;
    }
}
