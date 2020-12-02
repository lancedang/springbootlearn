package com.lance.pythonserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyResponse<T> {
    private int code;
    private String message;
    private T data;

    public static MyResponse success(Object data) {
        MyResponse response = new MyResponse(200, "success", data);
        return response;
    }

}
