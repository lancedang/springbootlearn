package com.lance.apollodemo.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 描述一个namespace key-value集合的变化，Apollo是以namespace为单位，
 * 通常发布属性更新时是将整个namespace作为一个整体统一发布,这个整体是Apollo server-client通信单位
 */
@Data
public class MyConfigChangeEvent {
    private String namespace;
    private Map<String, List<MyConfigChange>> configChangeMap;

    @Override
    public String toString() {
        return "MyConfigChangeEvent{" +
                "namespace='" + namespace + '\'' +
                ", configChangeMap=" + configChangeMap +
                '}';
    }
}
