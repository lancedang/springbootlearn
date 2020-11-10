// Copyright (C) 2020 Meituan
// All rights reserved
package com.lance.springboot.datasourcedemo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author qiankai07
 * @version 1.0
 * Created on 3/25/20 3:48 PM
 **/
@Component
public class MyCronScheduler {

    //@Scheduled(initialDelay = 10000, fixedDelay = 2000)
    @Scheduled(initialDelay = 10000, fixedRate = 2000)
    public void run() {
        System.out.println("hi");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}