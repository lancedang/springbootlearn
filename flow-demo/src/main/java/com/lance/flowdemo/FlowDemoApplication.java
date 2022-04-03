package com.lance.flowdemo;

import com.lance.flowdemo.constant.FlowKeyEnum;
import com.lance.flowdemo.entity.*;
import com.lance.flowdemo.manager.TxManager;
import com.lance.flowdemo.service.AdminFlowTemplateConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.*;
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

    @Autowired
    private TxManager txManager;

    public static void main(String[] args) {
        SpringApplication.run(FlowDemoApplication.class, args);
    }

    public void run(String... args) throws Exception {
        Properties properties = new Properties();
        final String springFactoryFile = "META-INF/spring.factories";
        Set<String> applicationContextInitializerSet = new HashSet<>();
        Set<String> applicationListenerSet = new HashSet<>();

        Enumeration<URL> resources = FlowDemoApplication.class.getClassLoader().getResources(springFactoryFile);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            UrlResource resource = new UrlResource(url);

            log.info("url={}", url);

            Properties t = PropertiesLoaderUtils.loadProperties(resource);
            properties.putAll(t);

            String property = t.getProperty(ApplicationContextInitializer.class.getName());
            String property2 = t.getProperty(ApplicationListener.class.getName());

            if (StringUtils.isNotBlank(property)) {
                applicationContextInitializerSet.addAll(Arrays.asList(property.split(",")));
                applicationListenerSet.addAll(Arrays.asList(property2.split(",")));
            }
        }

        log.info("set={}", applicationContextInitializerSet);
        log.info("set2={}", applicationListenerSet);


    }

    //测试TransactionSynchronizationManager
    public void run4(String... args) throws Exception{
        try {
            TxDemo txDemo = new TxDemo();
            txDemo.setName("demo1");
            txManager.addTx(txDemo);
        } catch (Exception e) {
            log.error("tx error, ", e);
        }
    }

    public void run3(String... args) throws Exception {
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
