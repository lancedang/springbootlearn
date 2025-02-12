package com.lance.batchjobdemo;

import com.lance.batchjobdemo.executor.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class BatchJobDemoApplication implements CommandLineRunner {

    @Autowired
    private TaskExecutor taskExecutor;

    public static void main(String[] args) {
        SpringApplication.run(BatchJobDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        TaskContext context = new TaskContext();

        List<BookInfo> bookInfoList = new ArrayList<>();
        BookInfo book1 = new BookInfo("zhangsang", 1);
        BookInfo book2 = new BookInfo("qiankai", 2);
        BookInfo book22 = new BookInfo("qiankai222", 2);
        BookInfo book3 = new BookInfo("wangwu", 3);

        bookInfoList.add(book1);
        bookInfoList.add(book2);
        bookInfoList.add(book22);
        bookInfoList.add(book3);

        //这里是具体类ReadQueueTask的class，RetryQueueTask 是abstract类，不能反射
        TaskResult result = taskExecutor.execute(bookInfoList, context, ReadQueueTask.class);


        log.info("result={}", result);

    }
}
