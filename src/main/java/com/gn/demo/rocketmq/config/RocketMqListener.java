package com.gn.demo.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 17:53
 * @Desc
 */
@Component
@Slf4j
public class RocketMqListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        log.info("收到消息啦...");
        if (CollectionUtils.isEmpty(msgs)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        for (MessageExt msg : msgs) {
            String message = new String(msg.getBody());
            log.info("接收到mq消息时间为:{}", new Date());
            log.info("接收到mq消息为:{}", message);
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
