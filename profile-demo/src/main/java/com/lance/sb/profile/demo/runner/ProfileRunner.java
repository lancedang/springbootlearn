package com.lance.sb.profile.demo.runner;

import com.lance.sb.profile.demo.property.InterfaceProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileRunner implements CommandLineRunner {

    //验证@Profile作用
    @Autowired
    private InterfaceProperty property;

    //验证读取不同application配置文件属性
    //指定active profile后将自动读取 application-env.properties中的属性
    @Value("${user.nickname}")
    private String name;

    @Override
    public void run(String... args) throws Exception {
        String property = this.property.readProperty();
        log.info(this.getClass().getSimpleName() + ", 未延迟执行");
        log.info("property={}", property);
        log.info("user.nickname={}", name);
    }
}
