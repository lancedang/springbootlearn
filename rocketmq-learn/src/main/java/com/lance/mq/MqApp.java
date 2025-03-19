package com.lance.mq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MqApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
