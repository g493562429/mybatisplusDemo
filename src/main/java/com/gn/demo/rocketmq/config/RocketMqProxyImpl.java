package com.gn.demo.rocketmq.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 17:03
 * @Desc
 */
@Slf4j
@Data
public class RocketMqProxyImpl implements MqProxy, InitializingBean, DisposableBean {

    private DefaultMQProducer producer;

    @Resource
    private ApplicationProperty applicationProperty;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.nonNull(this.producer)) {
            this.producer.start();
            log.info("[RocketMQ producer is starting]");
        } else {
            log.error("[RocketMQ] producer must not be null");
        }
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(this.producer)) {
            this.producer.shutdown();
            log.info("[RocketMQ producer has be closed]");
        } else {
            log.error("[RocketMQ] producer destroy must not be null");
        }
    }

    @Override
    public boolean sendMessage(String content, String logEntityTag, String logType) {
        if (Objects.isNull(this.producer)) {
            log.error("[RocketMQ] sendMessage producer is null, can not send message");
            return false;
        }
        String topic = this.applicationProperty.getTopic();
        try {
            Message message = new Message(topic, logEntityTag, content.getBytes(StandardCharsets.UTF_8));
            message.setKeys(logType);
            SendResult sendResult = this.producer.send(message);
            String msgId = sendResult.getMsgId();
            SendStatus sendStatus = sendResult.getSendStatus();
            log.info("[RocketMQ] Send successfully. topic=[{}], tag=[{}], content=[{}], keys=[{}], and msgId=[{}], sendStatus=[{}]", new Object[]{topic, logEntityTag, content, logType, msgId, sendStatus});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[RocketMQ] Message send fail!", e);
            return false;
        }
    }

    @Override
    public void syncSendMsg(String content, String logEntityTag, String logType) {
        if (Objects.isNull(this.producer)) {
            log.error("[RocketMQ] syncSendMsg producer is null, can not send message");
        }
        String topic = this.applicationProperty.getTopic();

        Message message = new Message(topic, logEntityTag, content.getBytes(StandardCharsets.UTF_8));
        message.setKeys(logType);
        try {
            this.producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    String msgId = sendResult.getMsgId();
                    SendStatus sendStatus = sendResult.getSendStatus();
                    RocketMqProxyImpl.log.info("[RocketMQ] sync send successfully. topic=[{}], tag=[{}], content=[{}], keys=[{}], and msgId=[{}], sendStatus=[{}]", new Object[]{topic, logEntityTag, content, logType, msgId, sendStatus});
                }

                @Override
                public void onException(Throwable e) {
                    RocketMqProxyImpl.log.error("[{RocketMQ}] sync fail!", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[RocketMQ] Message send fail!", e);
        }

    }
}
