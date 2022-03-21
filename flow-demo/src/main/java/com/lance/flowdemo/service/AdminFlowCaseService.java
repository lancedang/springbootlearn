package com.lance.flowdemo.service;

import com.lance.flowdemo.dao.AdminFlowCaseDAO;
import com.lance.flowdemo.dao.AdminFlowCaseItemDAO;
import com.lance.flowdemo.entity.AdminFlowCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminFlowCaseService {

    @Resource
    private AdminFlowCaseItemDAO adminFlowCaseItemDAO;

    @Resource
    private AdminFlowCaseDAO adminFlowCaseDAO;

    public void save(AdminFlowCase adminFlowCase) {
        adminFlowCaseDAO.save(adminFlowCase);
    }

    public AdminFlowCase findById(Long id) {
        return adminFlowCaseDAO.getById(id);
    }

    //caseId就是主键
    public AdminFlowCase findByTenantIdAndCaseId(Long tenantId, Long caseId) {
        return adminFlowCaseDAO.getByTenantIdAndCaseId(tenantId, caseId);
    }



}
