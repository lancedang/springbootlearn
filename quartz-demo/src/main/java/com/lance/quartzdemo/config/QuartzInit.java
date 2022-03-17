package com.lance.quartzdemo.config;

import com.google.gson.Gson;
import com.lance.quartzdemo.annotation.JobMetaAnnotation;
import com.lance.quartzdemo.constant.JobDataMapConstants;
import com.lance.quartzdemo.job.MyUniqueJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class QuartzInit {

    public static final String SCHEDULE_PACKAGE = "com.lance.quartzdemo.scheduler";

    @Autowired
    @Qualifier("quartzScheduler")
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
            prepareJob();
        } catch (SchedulerException e) {
            log.error("error, ", e);
        }
    }

    public void prepareJob() {
        Set<Method> allJobMethods = getAllJobMethods();

        allJobMethods.forEach(this::scheduleOneJob);

    }

    private void scheduleOneJob(Method method) {
        JobDataMap jobDataMap = buildJobDataMap(method);

        String jobId = method.getDeclaringClass().getCanonicalName() + "." + jobDataMap.getString(JobDataMapConstants.methodName);
        JobMetaAnnotation annotation = method.getAnnotation(JobMetaAnnotation.class);

        JobDetail jobDetail = JobBuilder.newJob(MyUniqueJob.class)
                .setJobData(jobDataMap)
                .withIdentity("jobKey-" + jobId, "jobGroup-" + jobId)
                .storeDurably(true)
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .usingJobData(jobDataMap)
                .withIdentity("triggerKey-" + jobId, "triggerGroup-" + jobId)
                .withSchedule(CronScheduleBuilder.cronSchedule(annotation.cron()))
                .build();

        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error("error, ", e);
        }
    }

    public static Set<Method> getAllJobMethods() {
        Reflections reflections = new Reflections(SCHEDULE_PACKAGE, MethodAnnotationsScanner.class);
        Set<Method> jobMethods = reflections.getMethodsAnnotatedWith(JobMetaAnnotation.class);
        return jobMethods;
    }

    public static JobDataMap buildJobDataMap(Method method) {

        JobMetaAnnotation annotation = method.getAnnotation(JobMetaAnnotation.class);
        String name = annotation.name();

        String params = annotation.jobParams();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobDataMapConstants.taskName, name);
        jobDataMap.put(JobDataMapConstants.methodName, method.getName());
        jobDataMap.put(JobDataMapConstants.methodParams, params);
        jobDataMap.put(JobDataMapConstants.className, method.getDeclaringClass().getCanonicalName());

        return jobDataMap;
    }

    public static void main(String[] args) {
        Set<Method> allJobMethods = getAllJobMethods();
        //log.info(allJobMethods + "");

        List<JobDataMap> collect = allJobMethods.stream().map((item) -> buildJobDataMap(item)).collect(Collectors.toList());
        log.info(new Gson().toJson(collect) + "");
    }

}
