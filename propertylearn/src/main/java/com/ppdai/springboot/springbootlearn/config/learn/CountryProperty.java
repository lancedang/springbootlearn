package com.ppdai.springboot.springbootlearn.config.learn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CountryProperty {

    @Value("${world.country.china.name}")
    private String name;

    @Value("${world.country.china.size}")
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
