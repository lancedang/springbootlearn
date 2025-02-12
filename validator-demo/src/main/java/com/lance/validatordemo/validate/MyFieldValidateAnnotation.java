package com.lance.validatordemo.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD}) //使用在类属性/字段上
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MyFieldValidator.class}) //绑定的真实校验逻辑类
public @interface MyFieldValidateAnnotation {
    //下面3个字段固定格式，照抄即可
    String message() default "my test error"; //错误描述信息
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
