package com.lance.apollodemo.service;

import com.lance.apollodemo.ApolloChangeHandler;
import com.lance.apollodemo.annotation.ApolloConfig;
import com.lance.apollodemo.apollo.ApolloConfigService;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @ApolloConfig
    private ApolloConfigService apolloConfigService;


    //server发生配置更新时，Apollo client重新去拉最新配置
    @ApolloChangeHandler
    private void change() {
        //fetch new config
    }
}
