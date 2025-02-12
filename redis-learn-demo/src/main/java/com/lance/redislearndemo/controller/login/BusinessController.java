package com.lance.redislearndemo.controller.login;

import com.lance.redislearndemo.Redis;
import com.lance.redislearndemo.aspect.MyExceptionAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/business")
@Slf4j
public class BusinessController {

    @IgnoreLoginAnnotation
    @RequestMapping(value = "/do", method = RequestMethod.GET)
    public String doBusiness(String name) {
        log.info("doBusiness={}", name);
        return "success";
    }

    @IgnoreLoginAnnotation
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    @MyExceptionAnnotation
    public String doException(String name) {
        if (true) {
            throw new RuntimeException("runtimeException in controller");
        }
        log.info("doBusiness={}", name);
        return "exception test";
    }
}
