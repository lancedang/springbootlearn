package com.lance.springboot.springbootlearn.config.array;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定义一个 pojo 类封装属性
 */
public class SingleCountyProperty {

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
