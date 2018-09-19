package com.lance.sb.profile.demo.property;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("fat")
public class FatProperty implements InterfaceProperty {

    @Bean
    public String readProperty() {
        return "fat profile";
    }

}
