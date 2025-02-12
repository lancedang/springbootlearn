package com.lance.messagedemo.model;

import lombok.Data;

@Data
public class MsgReceiveDO {
    private Long userId = 0L;

    private String phone = "";

    private String mail = "";
}
