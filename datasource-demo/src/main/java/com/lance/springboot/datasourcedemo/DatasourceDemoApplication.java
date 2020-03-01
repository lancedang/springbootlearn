package com.lance.springboot.datasourcedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class DatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceDemoApplication.class, args);
        System.out.println();
    }

}
