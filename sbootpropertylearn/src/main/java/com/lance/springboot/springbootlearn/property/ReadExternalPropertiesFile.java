package com.lance.springboot.springbootlearn.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = {"classpath:app.properties"})
public class ReadExternalPropertiesFile {
    @Autowired
    Environment env;

    @Bean
    public AppBean testBean() {
        AppBean testBean = new AppBean();
        testBean.setId(Integer.parseInt(env.getProperty("level")));
        return testBean;
    }
}
