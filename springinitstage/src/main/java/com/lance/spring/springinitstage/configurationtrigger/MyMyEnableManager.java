package com.lance.spring.springinitstage.configurationtrigger;

import com.lance.authority.autoconfigure2.ByImportAnnoBean;
import com.lance.authority.autoconfigure2.MyEnableAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MyEnableAnnotation
public class MyMyEnableManager {
    @Autowired
    public void set(ByImportAnnoBean bean) {
    }
}
