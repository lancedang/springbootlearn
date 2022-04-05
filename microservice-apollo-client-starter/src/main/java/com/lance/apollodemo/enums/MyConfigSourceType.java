package com.lance.apollodemo.enums;

public enum MyConfigSourceType {
    REMOTE("loaded from remote config service"),
    LOCAL("loaded from local cache"),
    NONE("load fail");
    private String desc;

    MyConfigSourceType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
