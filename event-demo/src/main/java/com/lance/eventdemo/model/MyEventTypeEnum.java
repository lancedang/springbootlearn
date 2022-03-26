package com.lance.eventdemo.model;

public enum MyEventTypeEnum {
    EMAIL_CONFIRM("101", true),
    EXPRESS_GET("102", true);

    private String code;
    private boolean persistent;

    MyEventTypeEnum(String code, boolean persistent) {
        this.code = code;
        this.persistent = persistent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }
}
