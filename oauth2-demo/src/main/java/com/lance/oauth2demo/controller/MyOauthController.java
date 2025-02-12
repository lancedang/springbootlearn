package com.lance.oauth2demo.controller;

import com.lance.oauth2demo.oauth.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/api")
public class MyOauthController {

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/test/read")
    public String read() {
        return "success";
    }

    //@PreAuthorize作用(没有配置该注解的话任何用户都可以访问(携带access token就行))
    //client携带user的username,pwd请求access token时，这个user需要具备ROLE_ADMIN role
    //user的ROLE_ADMIN在 MyUserDetailService中配置
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/test/readUse")
    public String readUserInfo(@RequestHeader("Authorization") String auth) {

        String token = auth.split(" ")[1];

        MyUserDetails principal = (MyUserDetails) tokenStore.readAuthentication(token).getPrincipal();

        User user = principal;

        return user.getUsername() + "::" + user.getPassword();
    }

    @GetMapping("/other/other1")
    public String other1() {
        return "other1";
    }

    @GetMapping("/other/other2")
    public String other2() {
        return "other2";
    }


    @GetMapping("/remain/remain1")
    public String remain1() {
        return "remain1";
    }

    @GetMapping("/remain/remain2")
    public String remain2() {
        return "remain1";
    }

}
