package com.lance.apollodemo;

import com.lance.apollodemo.atimport.model.SelfModel1;
import com.lance.apollodemo.atimport.model.SelfModel2;
import org.junit.Assert;
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

    @Autowired
    private SelfModel1 selfModel1;

    @Autowired
    private SelfModel2 selfModel2;

    @Test
    public void test() {
        System.out.println(name);
        System.out.println(environment.getProperty("student.name"));

        Assert.assertNotNull(selfModel1);
        Assert.assertNotNull(selfModel2);
    }

}
