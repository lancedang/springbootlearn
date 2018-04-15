package com.ppdai.springboot.springbootlearn.config.simple;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//首先指定 文件中字段属性，这里指定前缀
@ConfigurationProperties(prefix = "world.country.russia")
//进而，指定属性 所属的 property 文件，如果 property 文件不包含 以prefix 为前缀的属性，则忽略 property 文件
@PropertySource(value = "classpath:country.properties")
public class CountryProperty3 {

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
