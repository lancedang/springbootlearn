package com.lance.quartzdemo.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public JobDetail myJobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(HelloWorldSpringJob.class)
                .withIdentity("spring-job1", "spring-group1")
                .usingJobData("spring-job1_param", "spring-param1")
                .storeDurably(true)
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger myTrigger() {
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("spring-myTrigger", "spring-trigger-group1")
                .usingJobData("spring-trigger1_param", "spring-trigger1_param1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? 2022"))
                .forJob(myJobDetail())
                .build();

        return cronTrigger;
    }
}
