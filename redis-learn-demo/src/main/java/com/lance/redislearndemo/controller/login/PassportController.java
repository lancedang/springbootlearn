package com.lance.redislearndemo.controller.login;

import com.lance.redislearndemo.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/login")
@Slf4j
public class PassportController {

    public static final String tokenDelimiter = "_";
    public static final int tokenExpireTime = 60;

    @Autowired
    private Redis redis;

    @IgnoreLoginAnnotation
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    public String getToken(String name) {
        String token = name + tokenDelimiter + UUID.randomUUID();
        //设置token到redis,并设置过期时间
        redis.setValueWithTime(name, token, tokenExpireTime, TimeUnit.SECONDS);
        log.info("token={}", token);
        return token;
    }
}
