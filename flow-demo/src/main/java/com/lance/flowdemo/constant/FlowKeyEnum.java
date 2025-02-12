package com.lance.flowdemo.constant;

public enum FlowKeyEnum {
    KEY1("KEY1", "test"),
    KEY2("KEY2", "test");

    private String key;
    private String desc;

    FlowKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
