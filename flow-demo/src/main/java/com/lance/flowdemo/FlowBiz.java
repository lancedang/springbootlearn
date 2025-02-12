package com.lance.flowdemo;

import com.lance.flowdemo.constant.FlowStatusEnum;
import com.lance.flowdemo.entity.AdminFlowCase;
import com.lance.flowdemo.entity.AdminFlowCaseItem;
import com.lance.flowdemo.entity.AdminFlowTemplateConfig;
import com.lance.flowdemo.entity.CreateFlowParam;
import com.lance.flowdemo.postprocessor.MyBeanPostProcessor;
import com.lance.flowdemo.service.AdminFlowCaseItemService;
import com.lance.flowdemo.service.AdminFlowCaseService;
import com.lance.flowdemo.service.AdminFlowTemplateConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;

@Service
@Slf4j
public class FlowBiz {

    @Resource
    private AdminFlowCaseItemService adminFlowCaseItemService;

    @Resource
    private AdminFlowCaseService adminFlowCaseService;

    @Resource
    private AdminFlowTemplateConfigService adminFlowTemplateConfigService;

    //创建审批流
    public void createFlowCase(CreateFlowParam createFlowParam) {


        String flowKey = createFlowParam.getFlowKey();
        long tenantId = createFlowParam.getTenantId();

        //参数可取的直接信息
        AdminFlowCase adminFlowCase = new AdminFlowCase();
        adminFlowCase.setBizNo(createFlowParam.getBizNo());
        adminFlowCase.setFlowKey(flowKey);
        adminFlowCase.setFlowCaseName(createFlowParam.getTitle());
        adminFlowCase.setTenantId(createFlowParam.getTenantId());
        adminFlowCase.setStatus(FlowStatusEnum.INIT.getCode());

        //查模板配置获取相关信息
        AdminFlowTemplateConfig templateConfig = adminFlowTemplateConfigService.findByFlowKeyAndTenantId(flowKey, tenantId);
        String createBy = templateConfig.getCreateBy();
        Integer approveLevel = templateConfig.getApproveLevel();
        String bpmReviewers = templateConfig.getBpmReviewers();

        adminFlowCase.setApproveLevel(approveLevel);
        adminFlowCase.setInitiator(Long.parseLong(createBy));
        adminFlowCase.setBpmReviewer(bpmReviewers);

        //先保存case
        adminFlowCaseService.save(adminFlowCase);


        String[] viewers = bpmReviewers.split(";");
        Arrays.asList(viewers).forEach(viewerId -> {

            AdminFlowCaseItem adminFlowCaseItem = new AdminFlowCaseItem();
            adminFlowCaseItem.setCaseId(adminFlowCase.getId());
            adminFlowCaseItem.setFlowCaseName(adminFlowCase.getFlowCaseName());
            adminFlowCaseItem.setInitiator(adminFlowCase.getInitiator());
            adminFlowCaseItem.setTenantId(adminFlowCase.getTenantId());
            adminFlowCaseItem.setStatus(FlowStatusEnum.INIT.getCode());
            adminFlowCaseItem.setUserId(Long.parseLong(viewerId));

            adminFlowCaseItemService.save(adminFlowCaseItem);

        });


        AdminFlowCaseItem caseItem = new AdminFlowCaseItem();
    }

    //审批通过-db层状态的修改
    public void approve(AdminFlowCase adminFlowCase) {
        log.info("approve key={}", adminFlowCase.getFlowKey());
    }

    //审批拒绝-db层状态的修改
    public void reject(AdminFlowCase adminFlowCase) {
        log.info("reject key={}", adminFlowCase.getFlowKey());
    }

    //审批执行的具体逻辑-
    public void handle(AdminFlowCase adminFlowCase) {

        log.info("FlowBiz handle key={}", adminFlowCase.getFlowKey());

        Method method = MyBeanPostProcessor.key2MethodMaps.get(adminFlowCase.getFlowKey());
        Object o = MyBeanPostProcessor.key2TargetMaps.get(adminFlowCase.getFlowKey());

        try {
            method.invoke(o, adminFlowCase);
        } catch (Exception e) {
            log.error("invoke error, ", e);
        }

    }

}
