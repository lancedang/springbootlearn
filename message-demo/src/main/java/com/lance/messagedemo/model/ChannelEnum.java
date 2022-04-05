package com.lance.messagedemo.model;


public enum ChannelEnum {

    DUANXIN(0),
    EMAIL(1);


    private int code;

    ChannelEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
