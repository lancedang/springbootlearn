package com.lance.quartzdemo.reflect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class JobParam { //针对自定义注解的jobParams的POJO
    private String name;
    private String type;
    private String value;

    private Class<?> clazz;

    private Object paramValue;

}
