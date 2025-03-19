package com.lance.spring.springinitstage.util;

import lombok.Data;

import java.util.List;

@Data
public class MusicData {
    int code;
    List<MusicItem> data;
    String error;
}