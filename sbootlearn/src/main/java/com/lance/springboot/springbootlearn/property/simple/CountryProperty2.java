package com.lance.springboot.springbootlearn.property.simple;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component, 必须使 CountryProperty2 成为一个 bean
//成为 bean 有两种方式，一种是使用 @Component注解，另一种是在Application 类中, 用@EnableConfigurationProperties()来注解
@Component
@ConfigurationProperties(prefix = "world.country.china")
public class CountryProperty2 {

    private String name;

    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "name=" + name + ", size=" + size;
    }
}
