package com.lance.spring.springinitstage;

import com.lance.mq.ClockUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

//@Service
public class TXMsgService {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        TransactionListener transactionListener = new TransactionListener() {

            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                System.out.println("我告知broker我是啥状态");
                ClockUtil.sleep(60 * 10);
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("broker检查我是啥状态了");
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        };

        MessageExt messageExt = new MessageExt();
        messageExt.setTopic("test-tx");
        LocalTime now = LocalTime.now();
        String s = "测试事务消息:" + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
        messageExt.setBody(s.getBytes());


        TransactionMQProducer transactionProducer = new TransactionMQProducer("test-tx-group");
        transactionProducer.setNamesrvAddr("localhost:9876");

        transactionProducer.start();

        transactionProducer.setTransactionListener(transactionListener);

        transactionProducer.sendMessageInTransaction(messageExt, 1);

        TimeUnit.MINUTES.sleep(10);

        transactionProducer.shutdown();


    }

}
