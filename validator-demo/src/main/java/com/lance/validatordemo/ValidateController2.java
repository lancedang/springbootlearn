package com.lance.validatordemo;

import com.lance.validatordemo.validate.MyParamValidateAnnotation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
@Validated  //get 请求必须加入
public class ValidateController2 {

    @GetMapping("/test2") //这里需要要@RequestParam或@Pathvariable修饰，后面加入自定义validate注解
    public String test2(@RequestParam @MyParamValidateAnnotation String name) {
        return "success";
    }
}
