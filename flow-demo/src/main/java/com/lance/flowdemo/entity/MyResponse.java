package com.lance.flowdemo.entity;

import lombok.Data;

@Data
public class MyResponse {
    private int code;
    private String desc;
    private Object data;
}
