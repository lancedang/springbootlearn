package com.lance.validatordemo.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Target(value = {ElementType.PARAMETER}) //这里指定是parameter，标识是用在方法参数上
@Constraint(validatedBy = {MyParamValidator.class}) //绑定真实valida逻辑类
public @interface MyParamValidateAnnotation {
    String message() default "my test2 error";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
