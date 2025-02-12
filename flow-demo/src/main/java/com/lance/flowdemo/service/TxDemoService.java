package com.lance.flowdemo.service;

import com.lance.flowdemo.dao.TxDemoDAO;
import com.lance.flowdemo.entity.TxDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class TxDemoService {

    @Resource
    private TxDemoDAO txDemoDAO;

    //@Transactional
    public int saveTxDemo(TxDemo txDemo) {
/*        if (true) {
            throw new RuntimeException("test save exception");
        }*/
        return txDemoDAO.save(txDemo);
    }
}
