package com.lance.apollodemo.model;

import com.lance.apollodemo.enums.MyPropertyChangeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述一个key-value item的变化
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyConfigChange {
    private String namespace;
    private String key;
    private String oldValue;
    private String newValue;
    private MyPropertyChangeType changeType;
}
