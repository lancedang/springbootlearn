package com.lance.flowdemo.handler;

import com.lance.flowdemo.annotation.FlowAnnotation;
import com.lance.flowdemo.constant.FlowKeyEnum;
import com.lance.flowdemo.entity.AdminFlowCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyFlowHandler1 {

    @FlowAnnotation(keys = {FlowKeyEnum.KEY1})
    public void handle(AdminFlowCase adminFlowCase) {
        log.info("flow handle1 key={}", adminFlowCase.getFlowKey());
    }

    @FlowAnnotation(keys = {FlowKeyEnum.KEY2})
    public void handle2(AdminFlowCase adminFlowCase) {
        log.info("flow handle2 key={}", adminFlowCase.getFlowKey());
    }

    public void handle3(AdminFlowCase adminFlowCase) {
        log.info("flow handle3 key={}", adminFlowCase.getFlowKey());
    }

}
