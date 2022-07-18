package com.lance.rocketmqsdkdemo;


import com.lance.rocketmqsdkdemo.service.MonitorService;
import com.lance.rocketmqsdkdemo.service.MyMqService;
import com.xiaomi.mifi.scf.commons.rocketmq.MqConstants;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.NormalProducerWrapper;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.OrderProducerWrapper;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.TransactionProducerWrapper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RocketmqSdkDemoApplicationTests {

    @Autowired
    private MyMqService myMqService;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private NormalProducerWrapper normalProducerWrapper;

    @Autowired
    private OrderProducerWrapper orderProducerWrapper;


    @Autowired
    private TransactionProducerWrapper tranProducerWrapper;

    HashMap tags = new HashMap(
    );


    @Before
    public void before() {
        tags.put("evn", "test");
    }

    @Test
    public void testZk() {

    }

    @Test
    public void sendMsg() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        String topicNormal = "CL24628/sandbox_1_asset_repay_info_test";
        String topicOrder = "CL24628/canal_dispatch";
        String topicTran = "CL24628/test_transaction";

        String tag = "Tag";
        String key = "Id";
        byte[] body = "emq transfer normal msg ".getBytes();
        byte[] bodyOneway = "one way msg ".getBytes();
        byte[] bodyOrder = "order msg ".getBytes();
        byte[] bodyTans = "trans msg ".getBytes();

        int i = 0;
        while (i < 10) {

            Message messageNormal = new Message(topicNormal, tag, key, body);

            messageNormal.putUserProperty(MqConstants.isTransferKey, MqConstants.isTransferValue);

            myMqService.sendNormalMsg(messageNormal);

            Message messageOneway = new Message(topicNormal, tag, key, bodyOneway);

            normalProducerWrapper.sendOneway(messageOneway);

            int queueId = i % 2 + 1;

            String orderKey = key + "-" + i;
            String orderTag = tag + "-" + i;

            Message messageOrder = new Message(topicOrder, orderTag, orderKey, bodyOrder);
            orderProducerWrapper.send(messageOrder, queueId);

            Message messageTrans = new Message(topicTran, orderTag, orderKey, bodyTans);
            tranProducerWrapper.send(messageTrans);

            TimeUnit.SECONDS.sleep(1);
            i++;
        }
    }

}
