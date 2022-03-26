package com.lance.eventdemo.model;

public enum MyEventStatusEnum {
    INIT(1, "初始化"),
    DONE(2, "已完成");

    private int code;
    private String desc;

    MyEventStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
