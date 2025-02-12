package com.lance.batchjobdemo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Data
public class TaskContext {
    private int workThreadNumbers = 10;
    private int retryTimes = 3;
    private boolean exitWhenFail = false;
    private int sleep = 2000; //ms
    private final Map<String, Object> config = new LinkedHashMap<>(4);
}
