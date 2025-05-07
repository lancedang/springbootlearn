package com.lance.spring.springinitstage;

import com.lance.spring.springinitstage.order.BeanOrderRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringInitStageApplication.class)
@RunWith(SpringRunner.class)
public class SpringinitstageApplicationTests {

    @Autowired
    private BeanOrderRoom beanOrderRoom;

    @Test
    public void contextLoads() {
        beanOrderRoom.show();
    }

}
