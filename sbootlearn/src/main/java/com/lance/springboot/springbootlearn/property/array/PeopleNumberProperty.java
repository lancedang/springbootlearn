package com.lance.springboot.springbootlearn.property.array;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 同一行，以逗号分隔的属性值将组合成 list
 */
@Component
@ConfigurationProperties(prefix = "city.people")
public class PeopleNumberProperty {

    //以数组形式定义属性
    private List<String> number;

    //以 String 形式读取
    private String numberString;

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    public String getNumberString() {
        return numberString;
    }

    public void setNumberString(String numberString) {
        this.numberString = numberString;
    }
}
