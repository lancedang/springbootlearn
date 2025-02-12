package com.lance.quartzdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
@Slf4j
public class QuartzCoreConfig {

    //@Autowired
    private MySpringBeanJobFactory mySpringBeanJobFactory;

    //@Value("${}")
    private String jdbcUrl;

    @Bean
    public Properties quartzProperties() {
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", "qiankai-scheduler");
        props.put("org.quartz.scheduler.instanceId", "AUTO");
        props.put("org.quartz.threadPool.threadCount", "40");
        props.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        props.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        props.put("org.quartz.jobStore.dataSource", "qzDs");
        props.put("org.quartz.jobStore.isClustered", "true");
        props.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        props.put("org.quartz.jobStore.misfireThreshold", "60000");
        props.put("org.quartz.dataSource.qzDs.driver", "com.mysql.jdbc.Driver");
        props.put("org.quartz.dataSource.qzDs.provider", "hikaricp");
        props.put("org.quartz.dataSource.qzDs.URL", "jdbc:mysql://10.38.161.248:3306/lance_test?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        props.put("org.quartz.dataSource.qzDs.user", "mifi_admin");
        props.put("org.quartz.dataSource.qzDs.password", "OTg1NjJjNzlkNmI4ZTQzMGJhODRiMDIw");
        props.put("org.quartz.dataSource.qzDs.maxConnections", "30");
        props.put("org.quartz.jobStore.lockHandler.maxRetry", "7");
        props.put("org.quartz.jobStore.lockHandler.retryPeriod", "3000");
        //props.put("org.quartz.jobStore.lockHandler.class", "com.xiaomi.mifi.scf.saas.cloud.job.ScfStdRowLockSemaphore");
        return props;
    }

    @Bean
    public SchedulerFactoryBean getSchedulerFactory() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        //设置数据源，使用与项目统一数据源
        //schedulerFactoryBean.setDataSource(quartzDataSource);
        //schedulerFactoryBean.setJobFactory(mySpringBeanJobFactory);
        schedulerFactoryBean.setOverwriteExistingJobs(true);

        return schedulerFactoryBean;
    }

    @Bean(name = "quartzScheduler")
    public Scheduler getScheduler() {
        return getSchedulerFactory().getScheduler();
    }

}
