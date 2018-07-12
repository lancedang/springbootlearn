package com.lance.springboot.springbootlearn;

import com.lance.springboot.springbootlearn.property.array.CountryArrayBuilder;
import com.lance.springboot.springbootlearn.property.array.PeopleNumberProperty;
import com.lance.springboot.springbootlearn.property.map.ClothMapProperty;
import com.lance.springboot.springbootlearn.property.simple.CountryProperty2;
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

		//以数组形式读取属性
		CountryArrayBuilder countryArrayBuilder = applicationContext.getBean(CountryArrayBuilder.class);
		System.out.println("Country 数组：" + countryArrayBuilder.getStudent());

		//以 map 形式读取属性
		ClothMapProperty clothMapProperty = applicationContext.getBean(ClothMapProperty.class);
		System.out.println("Cloth Map：" + clothMapProperty.getCloth());

		//对以逗号分隔的属性值组成数组
		PeopleNumberProperty numberProperty = applicationContext.getBean(PeopleNumberProperty.class);
		System.out.println(numberProperty.getNumber());
		System.out.println(numberProperty.getNumberString());
	}
}
