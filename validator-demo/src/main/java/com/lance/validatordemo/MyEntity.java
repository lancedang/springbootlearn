package com.lance.validatordemo;

import com.lance.validatordemo.validate.MyFieldValidateAnnotation;
import lombok.Data;

@Data
public class MyEntity {

    //加入自定义validateAnnotation
    @MyFieldValidateAnnotation
    private String name;

    private int age;
}
