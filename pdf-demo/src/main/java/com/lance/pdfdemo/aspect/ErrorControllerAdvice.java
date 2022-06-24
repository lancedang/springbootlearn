package com.lance.pdfdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Throwable e) {

        response.setStatus(200);
        log.warn("[" + request.getRequestURI() + "]Uncaught exception", e);


        return response;
    }
}