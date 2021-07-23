package com.gn.demo.rocketmq.producer;

import com.gn.demo.rocketmq.config.MqProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 17:41
 * @Desc
 */
@Configuration
@EnableScheduling
@Slf4j
public class RocketMqProducer {

    @Autowired
    private MqProxy mqProxy;

    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    synchronized void run(){
        String uuid = UUID.randomUUID().toString();
        log.info("mq.uuid:{}", uuid);
        mqProxy.syncSendMsg("hello rocketMq", "test_tag", uuid);
    }
}
