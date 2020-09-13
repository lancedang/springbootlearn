package com.lance.spring.beannameautorproxy.beannameautoproxycreator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeanNameAutoProxyCreatorServiceImpl1 implements BeanNameAutoProxyCreatorService {
    @Override
    public void test(String name) {
        log.info(name);
    }
}
