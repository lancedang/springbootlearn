package com.lance.flowdemo;

import com.lance.flowdemo.constant.FlowKeyEnum;
import com.lance.flowdemo.entity.AdminFlowCase;
import com.lance.flowdemo.entity.AdminFlowTemplateConfig;
import com.lance.flowdemo.entity.CreateFlowParam;
import com.lance.flowdemo.entity.MyTestBean;
import com.lance.flowdemo.service.AdminFlowTemplateConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(exposeProxy = true)
@Slf4j
public class FlowDemoApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FlowBiz flowBiz;

    @Autowired
    private AdminFlowTemplateConfigService adminFlowTemplateConfigService;

    public static void main(String[] args) {
        SpringApplication.run(FlowDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //模拟多个独立审批流程
        //step-1)通过2个flow，同时执行每个节点处理逻辑

        AdminFlowCase case1 = new AdminFlowCase();
        case1.setFlowKey(FlowKeyEnum.KEY1.getKey());

        AdminFlowCase case2 = new AdminFlowCase();
        case2.setFlowKey(FlowKeyEnum.KEY2.getKey());

        flowBiz.approve(case1);
        flowBiz.handle(case1);

        flowBiz.approve(case2);
        flowBiz.handle(case2);

        MyTestBean myTestBean = applicationContext.getBean("myTestBean", MyTestBean.class);
        log.info("mytestbean={}", myTestBean.getName());
        log.info("mytestbean={}", myTestBean.getAddr());

    }

    public void run2(String... args) throws Exception {


        try {
            ((FlowDemoApplication)AopContext.currentProxy()).add();
        } catch (Exception e) {
            log.error("error, " , e);
        }
    }

    @Transactional
    public void add() {
        //step-01:插入template config
        String reviewer1 = "301";
        Long tenantId = 201L;
        Long userId = 101L;
        AdminFlowTemplateConfig templateConfig = new AdminFlowTemplateConfig();
        templateConfig.setTenantId(tenantId);
        templateConfig.setBpmReviewers(reviewer1);
        templateConfig.setApproveLevel(1);
        templateConfig.setCreateBy(userId + "");
        templateConfig.setDefaultInitiator(1);
        templateConfig.setKey(FlowKeyEnum.KEY1.getKey());
        templateConfig.setName("test config");
        templateConfig.setCreateTime(System.currentTimeMillis());
        templateConfig.setTitle("title1");
        templateConfig.setUpdateBy(userId + "");

        adminFlowTemplateConfigService.save(templateConfig);

        //step-02: 插入template

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //step-02:save case
        String bizNo = UUID.randomUUID().toString();
        log.info("bizNo={}", bizNo);

        CreateFlowParam createFlowParam = new CreateFlowParam();

        createFlowParam.setFlowKey(FlowKeyEnum.KEY1.getKey());
        createFlowParam.setBizNo(bizNo);
        createFlowParam.setInitiator(101);
        createFlowParam.setTenantId(tenantId);

        flowBiz.createFlowCase(createFlowParam);
    }
}
