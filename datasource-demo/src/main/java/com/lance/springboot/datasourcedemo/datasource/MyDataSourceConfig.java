// Copyright (C) 2020 Meituan
// All rights reserved
package com.lance.springboot.datasourcedemo.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author qiankai07
 * @version 1.0
 * Created on 3/1/20 6:32 PM
 **/
@Configuration
public class MyDataSourceConfig {

    @Bean("myDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }



}