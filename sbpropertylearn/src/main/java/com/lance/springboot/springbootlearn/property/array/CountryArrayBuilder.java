package com.lance.springboot.springbootlearn.property.array;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于初始化属性数组
 */
//用以取代@EnableConfiguraitionProperties
@Component
@ConfigurationProperties(prefix = "shanghai")
//声明属性文件地址(此案例不起作用，不能读取非默认配置文件)
//@PropertySource(value = {"classpath:countryArray.properties"})
public class CountryArrayBuilder {

    //用于保存属性对象
    private List<SingleCountyProperty> student;

    public List<SingleCountyProperty> getStudent() {
        return student;
    }

    public void setStudent(List<SingleCountyProperty> student) {
        this.student = student;
    }
}
