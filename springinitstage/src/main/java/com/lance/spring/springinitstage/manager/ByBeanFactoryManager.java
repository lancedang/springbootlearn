package com.lance.spring.springinitstage.manager;

import com.lance.authority.autoconfigure.BySpringFactoryFileBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//验证三方包/自定义包中的 @Configuration的激活方式
//2.spring.factories文件添加@Configuration配置类
@Component
public class ByBeanFactoryManager {
    @Autowired
    public ByBeanFactoryManager(BySpringFactoryFileBean bean) {

    }

}
