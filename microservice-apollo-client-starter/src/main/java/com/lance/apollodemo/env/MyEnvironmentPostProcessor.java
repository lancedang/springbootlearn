package com.lance.apollodemo.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//@Component
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private String propertyFile = "customer.properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
        PropertiesPropertySource propertiesPropertySource = null;

        try {
            //1.读取自定义文件
            //InputStream inputStream = new ClassPathResource(propertyFile).getInputStream();
            //下面这种读取文件方式读取不到数据
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyFile);
            Properties properties = new Properties();
            properties.load(inputStream);

            //2.创建PropertiesPropertySource
            propertiesPropertySource = new PropertiesPropertySource("customerSource", properties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3.将数据源加入到environment
        configurableEnvironment.getPropertySources().addFirst(propertiesPropertySource);
    }
}
