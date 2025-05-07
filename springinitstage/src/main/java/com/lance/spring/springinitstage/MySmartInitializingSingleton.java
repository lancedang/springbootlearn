package com.lance.spring.springinitstage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;

@Slf4j
public class MySmartInitializingSingleton implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        log.info("MySmartInitializingSingleton afterSingletonsInstantiated");
        System.out.println("MySmartInitializingSingleton afterSingletonsInstantiated");
    }
}
