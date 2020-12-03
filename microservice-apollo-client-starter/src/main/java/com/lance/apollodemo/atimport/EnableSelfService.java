package com.lance.apollodemo.atimport;

import com.lance.apollodemo.apollo.server.MockApolloServer;
import com.lance.apollodemo.atimport.model.SelfModel1;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
//@Import后面接MyImportBeanDefinationRegister实现类时，通常@Import用于修饰其他注解类-比如当下的EnableSelfService，
//Register实现类通过EnableSelfService注解的field进行判断
@Import(MyImportBeanDefinationRegister.class)
public  @interface EnableSelfService {
    String[] regions() default {"shanghai"};

    String basePackage();
}
