package com.lance.flowdemo.controller;

import com.lance.flowdemo.entity.MyTestBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    @RequestMapping(value = "/test", produces = MediaType.APPLICATION_XML_VALUE)
    public MyTestBean test() {
        MyTestBean bean = new MyTestBean();
        bean.setAddr("shanghai");
        bean.setName("qiankai");
        return bean;
    }


    @RequestMapping(value = "/test2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MyTestBean test2() {
        MyTestBean bean = new MyTestBean();
        bean.setAddr("上海");
        bean.setName("qiankai");
        return bean;
    }

    @RequestMapping(value = "/test3")
    public MyTestBean test3(@RequestParam String name) {
        MyTestBean bean = new MyTestBean();
        bean.setAddr(name);
        bean.setName("qiankai");
        return bean;
    }

    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    public MyTestBean test4(@RequestBody MyTestBean testBean) {

        testBean.setAddr("shanghai");
        testBean.setName("qiankai");

        return testBean;
    }

    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public MyTestBean test5() {

        if (true) {
            throw new RuntimeException("test errro");
        }
        return null;
    }


    @RequestMapping(value = "/test6", method = RequestMethod.POST)
    public String test6() {
        return "test string";
    }
}
