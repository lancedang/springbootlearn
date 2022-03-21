package com.lance.flowdemo.service;

import com.lance.flowdemo.dao.AdminFlowCaseDAO;
import com.lance.flowdemo.dao.AdminFlowCaseItemDAO;
import com.lance.flowdemo.dao.AdminFlowTemplateConfigDAO;
import com.lance.flowdemo.entity.AdminFlowCase;
import com.lance.flowdemo.entity.AdminFlowTemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminFlowTemplateConfigService {

    @Resource
    private AdminFlowTemplateConfigDAO adminFlowTemplateConfigDAO;


    public void save(AdminFlowTemplateConfig adminFlowCase) {
        adminFlowTemplateConfigDAO.save(adminFlowCase);
    }


    //caseId就是主键
    public AdminFlowTemplateConfig findByFlowKeyAndTenantId(String flowKey, Long tenantId) {
        return adminFlowTemplateConfigDAO.getByKeyAndTenantId(flowKey, tenantId);
    }



}
