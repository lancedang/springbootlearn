package com.ppdai.springboot.springbootlearn;

import com.ppdai.springboot.springbootlearn.config.simple.CountryProperty2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//通过 @EnableConfigurationProperties 来生成 property 类的 bean, 参数可以是 Class [] 数组
//如此以来，property 类就无需用 @Component 类型的注解来修饰
//@EnableConfigurationProperties({CountryProperty2.class})
public class SpringbootlearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootlearnApplication.class, args);
	}
}
