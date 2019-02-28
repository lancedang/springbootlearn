package com.lance.boot.actuator.actuatordemo.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kairosdb.client.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SelfKairosdbServiceTest {

    @Autowired
    private SelfKairosdbService kairosdbService;

    @Test
    public void testWriteMetricToKairosdb() {
        kairosdbService.writeMetricToKairosdb();
        List<QueryResult> queryResults = kairosdbService.findMetricPoint();
        String name = queryResults.get(0).getResults().get(0).getName();
        Assert.assertEquals(name, kairosdbService.getClass().getSimpleName()+".test");
    }

}
