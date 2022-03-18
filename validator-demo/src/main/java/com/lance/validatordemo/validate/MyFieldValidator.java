package com.lance.validatordemo.validate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j //第一个参数：绑定validate自定义注解，参数2：校验的目的类属性field类型
public class MyFieldValidator implements ConstraintValidator<MyFieldValidateAnnotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value) && value.startsWith("q")) {
            log.error("error");
            return false;
        }
        return true;
    }
}
