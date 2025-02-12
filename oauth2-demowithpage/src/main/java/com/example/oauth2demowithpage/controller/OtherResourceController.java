package com.example.oauth2demowithpage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other")
public class OtherResourceController {

    @GetMapping
    public String get() {
        return "other get";
    }
}
