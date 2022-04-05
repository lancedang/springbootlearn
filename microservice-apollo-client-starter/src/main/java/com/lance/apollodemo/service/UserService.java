package com.lance.apollodemo.service;

import com.lance.apollodemo.MyConfig;
import com.lance.apollodemo.spring.annotation.MyApolloConfigAnnotation;
import com.lance.apollodemo.spring.annotation.MyApolloConfigChangeListenerAnnotation;
import com.lance.apollodemo.model.MyConfigChangeEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
public class UserService {

    @MyApolloConfigAnnotation
    private MyConfig apolloConfig;


    //server发生配置更新时，Apollo client重新去拉最新配置
    @MyApolloConfigChangeListenerAnnotation
    public void change(MyConfigChangeEvent configChangeEvent) {
        //fetch new config
        log.info("receive change event: configChangeEvent={}", configChangeEvent);
    }
}
