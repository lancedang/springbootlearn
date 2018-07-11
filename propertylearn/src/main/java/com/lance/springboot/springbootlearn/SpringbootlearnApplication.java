package com.lance.springboot.springbootlearn;

import com.lance.springboot.springbootlearn.config.array.CountryArrayBuilder;
import com.lance.springboot.springbootlearn.config.map.ClothMapProperty;
import com.lance.springboot.springbootlearn.config.simple.CountryProperty2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//通过 @EnableConfigurationProperties 来生成 property 类的 bean, 参数可以是 Class [] 数组
//如此以来，property 类就无需用 @Component 类型的注解来修饰
@EnableConfigurationProperties({CountryProperty2.class})
public class SpringbootlearnApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootlearnApplication.class, args);
		CountryArrayBuilder countryArrayBuilder = applicationContext.getBean(CountryArrayBuilder.class);
		System.out.println("Country 数组：" + countryArrayBuilder.getStudent());

		ClothMapProperty clothMapProperty = applicationContext.getBean(ClothMapProperty.class);
		System.out.println("Cloth Map：" + clothMapProperty.getCloth());
	}
}
