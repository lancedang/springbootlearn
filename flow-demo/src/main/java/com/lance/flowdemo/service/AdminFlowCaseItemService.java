package com.lance.flowdemo.service;

import com.lance.flowdemo.dao.AdminFlowCaseItemDAO;
import com.lance.flowdemo.entity.AdminFlowCaseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminFlowCaseItemService {

    @Resource
    private AdminFlowCaseItemDAO adminFlowCaseItemDAO;

    public void save(AdminFlowCaseItem adminFlowCaseItem) {
        adminFlowCaseItemDAO.save(adminFlowCaseItem);
    }

}
