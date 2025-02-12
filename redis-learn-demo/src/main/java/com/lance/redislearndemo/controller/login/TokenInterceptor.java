package com.lance.redislearndemo.controller.login;

import com.lance.redislearndemo.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private Redis redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("enter TokenInterceptor preHandle");


        String requestURI = request.getRequestURI();
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = ((HandlerMethod) handler);
        Method method = handlerMethod.getMethod();

        //有些接口不需要token验证，比如最开始的用户登录接口
        //这种跳过(返回TRUE就行,返回TRUE意味着可以直接进入controller逻辑了)
        IgnoreLoginAnnotation annotation = method.getAnnotation(IgnoreLoginAnnotation.class);
        if (Objects.nonNull(annotation)) {
            log.info("method={} no need login", method.getName());
            return true;
        }

        //
        String tokenFromFrond = request.getHeader("token");
        log.info("tokenFromFrond={}", tokenFromFrond);
        //开始验证前端是否带着token
        if (StringUtils.isEmpty(tokenFromFrond)) {
            return false;
        }

        String[] splits = tokenFromFrond.split("_");
        String name = splits[0];

        //前端token和我们redis里是否一致
        String tokenInRedis = redis.getValue(name);
        log.info("tokenInRedis={}", tokenInRedis);

        boolean equals = tokenFromFrond.equals(tokenInRedis);
        if (equals) {
            redis.expire(name, PassportController.tokenExpireTime, TimeUnit.SECONDS);
        }
        return equals;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
