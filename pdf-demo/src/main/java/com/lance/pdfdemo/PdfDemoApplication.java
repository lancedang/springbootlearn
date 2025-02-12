package com.lance.pdfdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class PdfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfDemoApplication.class, args);
    }

}
