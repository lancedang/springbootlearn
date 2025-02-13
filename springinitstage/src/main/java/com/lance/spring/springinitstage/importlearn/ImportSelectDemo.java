package com.lance.spring.springinitstage.importlearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@MyImportAnnoForSelector
public class ImportSelectDemo  {
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        ImportSelectEntity1 b1 = applicationContext.getBean(ImportSelectEntity1.class);
        ImportSelectEntity2 b2 = applicationContext.getBean(ImportSelectEntity2.class);

        System.out.println(b1 == null);
        System.out.println(b2 == null);
    }
}
