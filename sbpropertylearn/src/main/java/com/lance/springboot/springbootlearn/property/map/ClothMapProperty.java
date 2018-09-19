package com.lance.springboot.springbootlearn.property.map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 将配置属性构造成 map 样式
 */
@Component
@ConfigurationProperties(prefix = "summer")
public class ClothMapProperty {

    private Map<String, String> cloth;

    public Map<String, String> getCloth() {
        return cloth;
    }

    public void setCloth(Map<String, String> cloth) {
        this.cloth = cloth;
    }
}
