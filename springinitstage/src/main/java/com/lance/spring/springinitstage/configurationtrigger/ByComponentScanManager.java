package com.lance.spring.springinitstage.configurationtrigger;

import com.lance.authority.autoconfigure.ByComponentScanBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

//验证三方包/自定义包中的 @Configuration的激活方式
//1.@ComponentScan触发
@Component
@ComponentScan(basePackages = {"com.lance.authority.autoconfigure"})
public class ByComponentScanManager {
    @Autowired
    public ByComponentScanManager(ByComponentScanBean bean) {
    }

}
