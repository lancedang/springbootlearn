package com.lance.springboot.springbootlearn.config.array;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 单纯为了生存数组 bean - CountryArrayBuilder
 * 这是 array package 下面 3 个类的入口
 * 1. 先定义单个类-纯 pojo
 * 2. 定义数组类，属性为 List<SingleClass>，需要@ConfigurationProperties(prefix = "") 修饰
 * 3. 定义 Config 配置类,生成 Array bean
 * 4. 可以使用 Array bean
 */
@Configuration
//@EnableConfigurationProperties(value = {CountryArrayBuilder.class})
public class CountryArrayConfig {
}
