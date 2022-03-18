package com.lance.validatordemo.validate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j //参数1：自定义validate annotation, 参数2: 所要修饰的方法参数类型
public class MyParamValidator implements ConstraintValidator<MyParamValidateAnnotation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value) && value.startsWith("q")) {
            log.error("error");
            return false;
        }
        return true;
    }
}
