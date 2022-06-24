package com.lance.pdfdemo.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

    public static final String traceIdKey = "traceId";
    public static final String schedulerTime = "schedulerTime";
    public static final String batchNumber = "batchNumber";
    public static final String schemaT = "schemaT"; //schema.table
    public static final String traceDelimiter = ":";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceFromFront = request.getParameter("traceId");
        String mdc = "MDCUtils.mdc()";
        String tempMdc = traceDelimiter + mdc;
        String traceId = StringUtils.isEmpty(traceFromFront) ? tempMdc : traceFromFront + tempMdc;
        MDC.clear();
        MDC.put(traceIdKey, traceId);
        return true;
    }
}
