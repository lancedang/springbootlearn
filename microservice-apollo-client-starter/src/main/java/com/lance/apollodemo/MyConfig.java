package com.lance.apollodemo;

import com.lance.apollodemo.enums.MyConfigSourceType;

import java.util.Set;

/**
 * 配置中心最上层接口，标识属性的各种操作:获取
 */
public interface MyConfig {

    String getProperty(String key, String defaultValue);

    //给这个配置中心服务加一个监听器，监听所有key
    void addChangeListener(MyConfigChangeListener configChangeListener);

    //给这个配置中心服务加一个监听器,只对感兴趣的key添加监听
    void addChangeListener(MyConfigChangeListener configChangeListener, Set<String> interestedKeys);

    MyConfigSourceType getConfigSourceType();
}
