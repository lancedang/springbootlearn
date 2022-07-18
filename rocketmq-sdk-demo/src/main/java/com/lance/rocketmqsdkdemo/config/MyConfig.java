package com.lance.rocketmqsdkdemo.config;

import api.config.ConfigKey;
import com.lance.rocketmqsdkdemo.service.MonitorService;
import com.xiaomi.infra.galaxy.emq.thrift.MessageService;
import com.xiaomi.mifi.scf.commons.mq.MessageClientFactory;
import com.xiaomi.mifi.scf.commons.mq.MessageConsumer;
import com.xiaomi.mifi.scf.commons.rocketmq.ScfMqClientFactory;
import com.xiaomi.mifi.scf.commons.rocketmq.consumer.NormalConsumerWrapper;
import com.xiaomi.mifi.scf.commons.rocketmq.consumer.OrderConsumerWrapper;
import com.xiaomi.mifi.scf.commons.rocketmq.metric.MqMetricCallback;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.NormalProducerWrapper;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.OrderProducerWrapper;
import com.xiaomi.mifi.scf.commons.rocketmq.producer.TransactionProducerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Slf4j
public class MyConfig {

    @Autowired
    private MonitorService monitorService;

    //region rocketUrl、队列、应用名、买点名信息
    private String queueName = "CL24628/sandbox_1_asset_repay_info_test";
    private String queueOrderName = "CL24628/canal_dispatch";

    //埋点名字不能包括"/"和"."
    private String realTopic = "sandbox_1_asset_repay_info_test";
    private String realOrderTopic = "canal_dispatch";
    private String realTansTopic = "test_transaction";

    private String appName = "emq_update_test2";

    private String normalConsumerGroup = "normal_consumer_group";
    private String normalProducerGroup = "normal_producer_group";

    private String orderConsumerGroup = "consumer_order";
    private String orderProducerGroup = "order_producer_group";

    private String tranConsumerGroup = "consumer_transaction";
    private String tranProducerGroup = "tran_producer_group";


    private String rocketUrl = "staging-cnbj2-rocketmq.namesrv.api.xiaomi.net:9876";


    private String metricEmqProducerName = realTopic + "_" + appName + "_producer_" + normalProducerGroup + "_emqbranch";
    private String metricRocketProducerName = realTopic + "_" + appName + "_producer_" + normalProducerGroup + "_rocketbranch";
    private String metricNormalProducerSendName = realTopic + "_" + appName + "_producer_" + normalProducerGroup;
    private String metricNormalProducerSendErrorName = realTopic + "_" + appName + "_producer_" + normalProducerGroup + "_error";


    private String metricOrderSendName = realOrderTopic + "_" + appName + "_producer_" + orderProducerGroup;
    private String metricOrderSendErrorName = realOrderTopic + "_" + appName + "_producer_" + orderProducerGroup + "_error";

    private String metricTranSendName = realTansTopic + "_" + appName + "_producer_" + tranProducerGroup;
    private String metricTranSendErrorName = realTansTopic + "_" + appName + "_producer_" + tranProducerGroup + "_error";


    private String metricNormalConsumeName = realTopic + "_" + appName + "_consumer_" + normalConsumerGroup;
    private String metricNormalConsumeErrorName = realTopic + "_" + appName + "_consumer_" + normalConsumerGroup + "_error";
    private String metricNormalConsumerSubscribeName = realTopic + "_" + appName + "_consumer_" + normalConsumerGroup + "_subscribe";

    private String metricOrderConsumeName = realOrderTopic + "_" + appName + "_consumer_" + orderConsumerGroup;
    private String metricOrderConsumeErrorName = realOrderTopic + "_" + appName + "_consumer_" + orderConsumerGroup + "_error";
    private String metricOrderConsumerSubscribeName = realOrderTopic + "_" + appName + "_consumer_" + orderConsumerGroup + "_subscribe";

    private String metricTranConsumeName = realTansTopic + "_" + appName + "_consumer_" + tranConsumerGroup;
    private String metricTranConsumeErrorName = realTansTopic + "_" + appName + "_consumer_" + tranConsumerGroup + "_error";
    private String metricTranConsumerSubscribeName = realTansTopic + "_" + appName + "_consumer_" + tranConsumerGroup + "_subscribe";

    //endregion


    @Bean
    public MessageService.Iface messageClient(
            @Value("${emq.maxTotalConnections}") int maxTotalConnections,
            @Value("${emq.maxTotalConnectionsPerRoute}") int maxTotalConnectionsPerRoute,
            @Value("${emq.endPoint}") String endPoint) {
        return MessageClientFactory
                .getInstance(maxTotalConnections, maxTotalConnectionsPerRoute, endPoint);
    }

    //normal消息生产者
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public NormalProducerWrapper normalProducerWrapper(@Autowired(required = false) MessageService.Iface messageClient) {
        Properties properties = new Properties();
        properties.setProperty(ConfigKey.PRODUCER_GROUP, normalProducerGroup);
        properties.setProperty(ConfigKey.NAME_SERVER_ADDR, rocketUrl);

        //传递应用名-埋点信息生产者/消费者，不传为默认system
        properties.setProperty("applicationName", appName);


        //tags可封装机器ip/时间戳等其他维度信息，Prometheus查询可以指定具体维度
        HashMap<String, String> tags = new HashMap<>();

        NormalProducerWrapper normalProducerWrapper = ScfMqClientFactory.createNormalProducerWrapper(properties, messageClient, new MqMetricCallback() {
            @Override
            public void metricSendEmqBranch() {
                //emq分支埋点统计
                log.info("normalProducer callback by emq");
                monitorService.counter(metricEmqProducerName, tags);
            }

            @Override
            public void metricSendRocketBranch() {
                //rocket分支埋点统计
                log.info("normalProducer callback by rocket");
                monitorService.counter(metricRocketProducerName, tags);
            }

            @Override
            public void metricSendError() {
                //此处也可自行加入error埋点
                log.info("normalProducer callback error");
            }

            @Override
            public void metricSend() {
                //发送总计埋点-逻辑应该是上述2个分支之和
                log.info("normalProducer send callback");
                monitorService.counter(metricNormalProducerSendName, tags);
            }
        });

        log.info("NormalProducerWrapper class={}", normalProducerWrapper.getClass().getName());

        return normalProducerWrapper;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerWrapper orderProducerWrapper() {
        Properties properties = new Properties();

        properties.setProperty(ConfigKey.PRODUCER_GROUP, orderProducerGroup);
        properties.setProperty(ConfigKey.NAME_SERVER_ADDR, rocketUrl);

        properties.setProperty("applicationName", appName);

        HashMap<String, String> tags = new HashMap<>();

        OrderProducerWrapper orderProducerWrapper = ScfMqClientFactory.createOrderProducerWrapper(properties, new MqMetricCallback() {
            @Override
            public void metricSendError() {
                log.info("order producer send error callback");
                monitorService.counter(metricOrderSendErrorName, tags);
            }

            @Override
            public void metricSend() {
                log.info("order producer send callback");
                monitorService.counter(metricOrderSendName, tags);
            }
        });

        log.info("NormalProducerWrapper class={}", orderProducerWrapper.getClass().getName());

        return orderProducerWrapper;
    }


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducerWrapper transactionProducerWrapper() {
        Properties properties = new Properties();

        properties.setProperty(ConfigKey.PRODUCER_GROUP, tranProducerGroup);
        properties.setProperty(ConfigKey.NAME_SERVER_ADDR, rocketUrl);

        properties.setProperty("applicationName", appName);

        HashMap<String, String> tags = new HashMap<>();

        ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

        TransactionProducerWrapper transactionProducer = ScfMqClientFactory.createTransactionProducerWrapper(properties, new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String transactionId = msg.getTransactionId();
                localTrans.put(transactionId, 0);

                try {
                    log.info("executing local transaction");
                    Thread.sleep(6000);
                    log.info("local transaction success");
                    localTrans.put(transactionId, 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    localTrans.put(transactionId, 2);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }

                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String transactionId = msg.getTransactionId();
                Integer integer = localTrans.get(transactionId);
                switch (integer) {
                    case 0:
                        return LocalTransactionState.UNKNOW;
                    case 1:
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case 2:
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }

        }, new MqMetricCallback() {
            @Override
            public void metricSendError() {
                log.info("order producer send error callback");
                monitorService.counter(metricTranSendErrorName, new HashMap<>());

            }

            @Override
            public void metricSend() {
                log.info("order producer send callback");
                monitorService.counter(metricTranSendName, new HashMap<>());
            }
        });

        return transactionProducer;
    }

    @Bean(name = "productMessageConsumer")
    public MessageConsumer productMessageConsumer(
            MessageService.Iface messageClient,
            @Value("${emq.periodTime}") int periodTime) {
        MessageConsumer messageConsumer = new MessageConsumer();
        messageConsumer.setMessageClient(messageClient);

        String realTopic = queueName.split("/")[1];

        String metricEmqConsumerName = realTopic + "_" + appName + "_consumer_emq";
        HashMap<String, String> tags = new HashMap<>();

        messageConsumer.setMessageConsumerCallback((msg) -> {
            log.info("consumer by emq, {}", msg);
            monitorService.counter(metricEmqConsumerName, tags);
            return true;
        });

        messageConsumer.setQueueName(queueName);
        messageConsumer.setPeriodTime(periodTime);
        messageConsumer.consume();
        return messageConsumer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "normalConsumer")
    public NormalConsumerWrapper normalConsumer() throws MQClientException {
        Properties properties = new Properties();

        properties.setProperty(ConfigKey.CONSUMER_GROUP, normalConsumerGroup);   //订阅组名
        properties.setProperty(ConfigKey.NAME_SERVER_ADDR, rocketUrl);
        properties.setProperty(ConfigKey.CONSUME_FROM_WHERE, ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET.name());

        properties.setProperty("applicationName", appName);

        HashMap<String, String> tags = new HashMap<>();

        NormalConsumerWrapper normalConsumer = ScfMqClientFactory.createNormalConsumerWrapper(properties, new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                byte[] bodyBytes = messageExt.getBody();

                if (bodyBytes.length > 100) {
                    //消费成功埋点-这个自行埋点即可，不需要实现sdk callback接口
                    monitorService.counter(metricNormalConsumeName, tags);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } else {
                    //消费失败埋点
                    monitorService.counter(metricNormalConsumeErrorName, tags);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        }, new MqMetricCallback() {
            @Override
            public void metricSubscribe() {
                log.info("normal Consumer Subscribe  callback");
                //这个是订阅埋点
                monitorService.counter(metricNormalConsumerSubscribeName, tags);
            }
        });

        normalConsumer.subscribe(queueName, "*");

        return normalConsumer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderConsumerWrapper orderConsumerWrapper() throws MQClientException {
        Properties properties = new Properties();

        properties.setProperty(ConfigKey.CONSUMER_GROUP, orderConsumerGroup);   //订阅组名
        properties.setProperty(ConfigKey.NAME_SERVER_ADDR, rocketUrl);
        properties.setProperty(ConfigKey.CONSUME_FROM_WHERE, ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET.name());

        properties.setProperty("applicationName", appName);

        OrderConsumerWrapper consumerWrapper = ScfMqClientFactory.createOrderConsumerWrapper(properties, new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                log.info("enter order consumer");
                MessageQueue messageQueue = context.getMessageQueue();
                int queueId = messageQueue.getQueueId();
                for (MessageExt msg : msgs) {

                    log.info("queueId={}, body={}, key={},tag={},tranId={}", msg.getQueueId(), new String(msg.getBody()),
                            msg.getKeys(),
                            msg.getTags(),
                            msg.getTransactionId());
                }
                monitorService.counter(metricOrderConsumeName, new HashMap<>());
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumerWrapper.subscribe(realOrderTopic, "*");

        return consumerWrapper;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "transNormalConsumer")
    public NormalConsumerWrapper transNormalConsumer() throws MQClientException {
        Properties properties = new Properties();

        properties.setProperty(ConfigKey.CONSUMER_GROUP, tranConsumerGroup);   //订阅组名
        properties.setProperty(ConfigKey.NAME_SERVER_ADDR, rocketUrl);
        properties.setProperty(ConfigKey.CONSUME_FROM_WHERE, ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET.name());

        properties.setProperty("applicationName", appName);

        HashMap<String, String> tags = new HashMap<>();

        NormalConsumerWrapper normalConsumer = ScfMqClientFactory.createNormalConsumerWrapper(properties, new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                byte[] bodyBytes = messageExt.getBody();
                log.info("trans consumer ={}", new String(bodyBytes));
                //消费成功埋点-这个自行埋点即可，不需要实现sdk callback接口
                monitorService.counter(metricTranConsumeName, tags);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        }, null);

        normalConsumer.subscribe(realTansTopic, "*");

        return normalConsumer;
    }


}
