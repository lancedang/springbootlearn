package com.lance.apollodemo.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
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

        //前提：构建一个PropertiesPropertySource对象，它作为中间承载自定义.properties文件内容
        //然后将其放入environment的sources集合中即可
        PropertiesPropertySource propertiesPropertySource = null;

        try {
            //1.这种读取不到文件
            //InputStream inputStream = new ClassPathResource(propertyFile).getInputStream();

            //方式1：下面这种读取文件方式读取数据到Properties对象
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyFile);
            Properties properties = new Properties();
            properties.load(inputStream);

            //方式2：工具类读取数据到Properties对象
            Properties properties1 = PropertiesLoaderUtils.loadAllProperties(propertyFile);

            //2.使用Properties创建PropertiesPropertySource
            propertiesPropertySource = new PropertiesPropertySource("customerSource", properties1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3.将数据源加入到environment
        configurableEnvironment.getPropertySources().addFirst(propertiesPropertySource);
    }
}
