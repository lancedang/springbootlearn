package com.lance.quartzdemo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class HelloWorldJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("hello world job {}", context.getJobDetail().getKey());
    }

    public static void main(String[] args) throws SchedulerException {
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();

        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloWorldJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("job1_param", "param1")
                .build();

        JobDetail jobDetail2 = JobBuilder.newJob(HelloWorldJob.class)
                .withIdentity("job2", "group2")
                .usingJobData("job2_param", "param2")
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "trigger-group1")
                .usingJobData("trigger1_param", "trigger1_param1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? 2022"))
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
        //scheduler.scheduleJob(jobDetail2, cronTrigger);
    }
}
