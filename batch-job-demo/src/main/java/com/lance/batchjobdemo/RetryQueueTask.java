package com.lance.batchjobdemo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;

@Data
@Slf4j
public abstract class RetryQueueTask<T> implements Callable<TaskResult> {

    //存储并行需要操作的任务
    private Queue<T> taskQueue;

    //存储任务配置
    private TaskContext taskContext;


    @Override
    public TaskResult call() throws Exception {

        //这里统计每一组执行情况，一个call代表线程池的一次提交，一次可能处理queue中的若干个item
        TaskResult<TaskResult> finalResult = new TaskResult<>();
        int successCount = 0;
        int totalCount = 0;
        int failCount = 0;
        int skipCount = 0;

        RetryTemplate template = RetryTemplateFactory.getTemplate(taskContext.getRetryTimes(), taskContext.getSleep(), Exception.class);

        while (Objects.nonNull(taskQueue) && !CollectionUtils.isEmpty(taskQueue)) {
            T task = this.taskQueue.poll();
            //这里需要加空判断，poll可能返回空
            if (Objects.nonNull(task)) {
                try {
                    //每次执行一个任务
                    TaskResult taskResult = template.execute((context) -> handleOne(task, taskContext));

                    finalResult.getResultList().add(taskResult);
                    successCount += taskResult.getSuccessCount();
                    totalCount += taskResult.getTotalCount();
                    skipCount += taskResult.getSkipCount();
                    failCount += taskResult.getFailCount();

                } catch (Throwable e) {
                    log.error("error, ", e);
                    finalResult.setFailCount(finalResult.getFailCount() + 1);
                }
            }

        }
        finalResult.setSkipCount(skipCount);
        finalResult.setSuccessCount(successCount);
        finalResult.setTotalCount(totalCount);
        finalResult.setFailCount(failCount);
        return finalResult;
    }

    protected abstract TaskResult handleOne(T task, TaskContext taskContext);
}
