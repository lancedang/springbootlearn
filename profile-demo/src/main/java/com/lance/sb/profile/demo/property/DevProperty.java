package com.lance.sb.profile.demo.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevProperty implements InterfaceProperty{

    @Bean
    public String readProperty() {
        return "dev profile";
    }

}
