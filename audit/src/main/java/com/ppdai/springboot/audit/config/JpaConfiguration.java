package com.ppdai.springboot.audit.config;


import com.ppdai.springboot.audit.dao.BookDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
