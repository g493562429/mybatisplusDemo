package com.gn.demo.rocketmq.consumer;

import com.gn.demo.rocketmq.config.RocketMqListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 17:46
 * @Desc
 */
@Component
@Slf4j
public class RocketMqConsumer {
    @Value("${gn.ceshi.mq.rocketmq.nameSvrAddr}")
    private String nameSvrAddr;

    @Value("${gn.ceshi.mq.topic}")
    private String topic;

    @Value("${gn.ceshi.mq.tag:test_tag}")
    private String tag;

    @Autowired
    private RocketMqListener rocketMqListener;

    @PostConstruct
    public void init() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("gn-ceshi-mq-consumer");
        consumer.setNamesrvAddr(nameSvrAddr);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(topic, tag);
        consumer.registerMessageListener(rocketMqListener);
        consumer.start();
        log.info("RocketMqConsumer 启动, nameSrvAddr:{}, topic:{}, tag:{}", nameSvrAddr, topic, tag);
    }
}
