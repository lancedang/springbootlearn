package com.lance.redislearndemo.controller.login;

import com.lance.redislearndemo.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/business")
@Slf4j
public class BusinessController {

    @RequestMapping(value = "/do", method = RequestMethod.GET)
    public String doBusiness(String name) {
        log.info("doBusiness={}", name);
        return "success";
    }
}
