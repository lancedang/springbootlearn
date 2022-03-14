package com.lance.batchjobdemo;

public class ReadQueueTask extends RetryQueueTask<BookInfo>{

    @Override
    public TaskResult handleOne(BookInfo task, TaskContext taskContext) {

        String name = task.getName();
        int price = task.getPrice();

        return getResult(name);
    }

    //模拟各种结果
    private TaskResult getResult(String name) {

        if (name.startsWith("q")) {
            return TaskResult.successResult(name);
        }else if(name.startsWith("z")){
            return TaskResult.skipResult(name);
        }else {
            return TaskResult.failResult(name);
        }

    }
}
