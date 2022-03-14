package com.lance.batchjobdemo.executor;

import com.lance.batchjobdemo.RetryQueueTask;
import com.lance.batchjobdemo.TaskContext;
import com.lance.batchjobdemo.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

//集合对象，分批执行入口，执行器
@Component
@Slf4j
public class TaskExecutor {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 给我一个集合，我把他分解成若干bucket分别执行，每个bucket给一个thread执行
     *
     * @param taskList
     * @param taskContext
     * @param clazz
     */
    public TaskResult execute(List taskList, TaskContext taskContext, Class<? extends RetryQueueTask> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


        TaskResult finalResult = new TaskResult();
        int successCount = 0;
        int totalCount = 0;
        int failCount = 0;
        int skipCount = 0;
        List<TaskResult> results = new ArrayList<>();
        String message = "";


        try {
            Queue queue = new ConcurrentLinkedQueue();
            queue.addAll(taskList);

            if (Objects.isNull(taskContext)) {
                taskContext = new TaskContext();
            }

            List<Future<TaskResult>> futureResult = new ArrayList<>();

            for (int i = 0; i < taskContext.getWorkThreadNumbers(); i++) {
                Constructor<? extends RetryQueueTask> constructor = clazz.getConstructor();
                //创建n个QueueTask分解工作，几个QueueTask共同操作，同一个QueueList,每个QueueTask执行多少个队列元素，看cpu
                RetryQueueTask retryQueueTask = constructor.newInstance();
                retryQueueTask.setTaskQueue(queue);
                retryQueueTask.setTaskContext(taskContext);

                //每个QueueTask提交线程池
                Future submit = threadPoolTaskExecutor.submit(retryQueueTask);

                futureResult.add(submit);
            }


            //阻塞等待所有结果
            for (int i = 0; i < futureResult.size(); i++) {

                TaskResult taskResult = futureResult.get(i).get();

                totalCount += taskResult.getTotalCount();
                successCount += taskResult.getSuccessCount();
                failCount += taskResult.getFailCount();
                skipCount += taskResult.getSkipCount();

                finalResult.getResultList().addAll(taskResult.getResultList());

            }

            message = "totalCount=";

        } catch (Exception e) {
            failCount += 1;
            message = e.getMessage();
            log.error("executor error,", e);
        }

        //finalResult.setResultList(results);
        finalResult.setTotalCount(totalCount);
        finalResult.setSuccessCount(successCount);
        finalResult.setFailCount(failCount);
        finalResult.setSkipCount(skipCount);
        finalResult.setMessage(message);

        return finalResult;
    }

}
