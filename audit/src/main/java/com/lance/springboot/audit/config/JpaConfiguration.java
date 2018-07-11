package com.lance.springboot.audit.config;


import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

//只要将 AuditorAware 作为 bean 注入上下文即可，达到此效果方式有多种
//@Configuration
//@EnableJpaRepositories(basePackageClasses = {BookDAO.class})
//@EnableJpaAuditing
public class JpaConfiguration {

    @Bean
    AuditorAware<String> auditorProvider() {
        UserAuditorAware userAuditorAware = new UserAuditorAware();
        return userAuditorAware;
    }

}
