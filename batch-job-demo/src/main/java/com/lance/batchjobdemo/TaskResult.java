package com.lance.batchjobdemo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class TaskResult<R> {

    private int totalCount;

    private int successCount;

    private int failCount;

    private int skipCount;

    private String message;

    private List<R> resultList = new ArrayList();

    public TaskResult() {
    }

    public TaskResult(int totalCount, int successCount, int failCount, int skipCount, String message) {
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.failCount = failCount;
        this.skipCount = skipCount;
        this.message = message;
    }

    public static TaskResult successResult(String message) {
        return new TaskResult(1, 1, 0, 0, message);
    }

    public static TaskResult failResult(String message) {
        return new TaskResult(1, 0, 1, 0, message);
    }

    public static TaskResult skipResult(String message) {
        return new TaskResult(1, 0, 0, 1, message);
    }

}
