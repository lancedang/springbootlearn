package com.lance.rocketmqsdkdemo.service;

import com.xiaomi.mifi.scf.commons.rocketmq.MqProduceResult;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.NormalProducerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyMqService {

    @Autowired
    private NormalProducerWrapper normalProducerWrapper;

    public String sendNormalMsg(Message message) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        MqProduceResult result = normalProducerWrapper.send(message);

        return "success";
    }

}
