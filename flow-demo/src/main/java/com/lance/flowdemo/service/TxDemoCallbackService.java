package com.lance.flowdemo.service;

import com.lance.flowdemo.dao.TxDemoCallbackDAO;
import com.lance.flowdemo.entity.TxDemoCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TxDemoCallbackService {

    @Resource
    private TxDemoCallbackDAO txDemoCallbackDAO;

    public int saveTxDemoCallback(TxDemoCallback txDemoCallback) {
        return txDemoCallbackDAO.save(txDemoCallback);
    }
}
