package com.lance.sb.profile.demo.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileRunner2 implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        log.info(this.getClass().getSimpleName() + ", 未延迟执行");
    }
}
