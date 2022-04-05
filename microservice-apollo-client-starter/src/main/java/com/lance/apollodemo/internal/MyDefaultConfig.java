package com.lance.apollodemo.internal;

import com.google.common.collect.Lists;
import com.lance.apollodemo.MyConfig;
import com.lance.apollodemo.MyConfigChangeListener;
import com.lance.apollodemo.enums.MyConfigSourceType;
import com.lance.apollodemo.model.MyConfigChangeEvent;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class MyDefaultConfig implements MyConfig {

    //这个配置中心上的所有listener
    private final List<MyConfigChangeListener> listeners = Lists.newCopyOnWriteArrayList();

    public static Map<String, MyConfig> configMap = new ConcurrentHashMap<>();

    private String namespace;

    //模拟一个根据namespace构造配置中心的逻辑
    public static synchronized MyConfig getConfig(String namespace) {
        MyConfig myConfig = configMap.get(namespace);
        if (myConfig == null) {
            MyDefaultConfig defaultConfig = new MyDefaultConfig();
            defaultConfig.setNamespace(namespace);
            configMap.put(namespace, defaultConfig);
            myConfig = defaultConfig;
        }
        return myConfig;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return null;
    }

    @Override
    public void addChangeListener(MyConfigChangeListener configChangeListener) {
        listeners.add(configChangeListener);
    }

    @Override
    public void addChangeListener(MyConfigChangeListener configChangeListener, Set<String> interestedKeys) {

    }

    @Override
    public MyConfigSourceType getConfigSourceType() {
        return null;
    }

    //这里加入一个最简触发属性更新的入口，先手动调用这个来触发属性变化通知，
    //原则可以通过定时轮训那种来发送通知
    public void fireConfigChange(final MyConfigChangeEvent changeEvent) {
        for (MyConfigChangeListener listener : listeners) {
            listener.onChange(changeEvent);
        }
    }

    @Override
    public String toString() {
        return "MyDefaultConfig{" +
                "namespace='" + namespace + '\'' +
                '}';
    }
}
