package com.lance.apollodemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ApolloDemoApplication.class)
@RunWith(SpringRunner.class)
public class ApolloDemoApplicationTests {

    @Value("${student.name}")
    private String name;

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {
        System.out.println(name);
        System.out.println(environment.getProperty("student.name"));
    }

}
