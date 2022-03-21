package com.lance.flowdemo.constant;

public enum FlowStatusEnum {
    INIT(0, "初始化"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败");
    private int code;
    private String desc;

    FlowStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
