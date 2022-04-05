package com.lance.apollodemo;

import com.lance.apollodemo.model.MyConfigChangeEvent;

/**
 * 配置变化监听器/回调接口，就是定义发生属性变化的时候，如何处理这种变化的接口
 * 处理的对象是以某个namespace为整体的单位
 */
public interface MyConfigChangeListener {
    void onChange(MyConfigChangeEvent configChangeEvent);
}
