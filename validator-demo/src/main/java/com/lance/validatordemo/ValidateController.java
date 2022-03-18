package com.lance.validatordemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@Validated //这个字段是为了作用在get method上，post不受影响
public class ValidateController {

    //@RequestBody 必须要加上， @RequestBody配套的是@Validated来启用我们自定义校验注解
    @PostMapping(value = "/test")
    public String test(@RequestBody @Validated MyEntity myEntity) {
        log.info("test controller");
        return "success";
    }

}
