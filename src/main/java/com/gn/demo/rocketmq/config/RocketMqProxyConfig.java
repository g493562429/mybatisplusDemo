package com.gn.demo.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 16:59
 * @Desc
 */
@ConditionalOnProperty(value = "gn.ceshi.mq.enable", havingValue = "true")
@Configuration
@EnableConfigurationProperties({ApplicationProperty.class})
@Slf4j
public class RocketMqProxyConfig {
    @Resource
    private ApplicationProperty applicationProperty;

    @Bean
    @ConditionalOnMissingBean({RocketMqProxyImpl.class})
    @ConditionalOnProperty(name = {"gn.ceshi.mq.mqType"}, havingValue = "RocketMQ")
    public RocketMqProxyImpl rocketMqProxyImpl() {
        RocketMqProxyImpl rocketMqProxy = new RocketMqProxyImpl();
        ApplicationProperty.RocketMQProperty rocketmq = this.applicationProperty.getRocketmq();
        log.info("rocketmq.config:{}", rocketmq.toString());
        String producerGroup = rocketmq.getProducerGroup();
        String namesrvAddr = rocketmq.getNamesrvAddr();
        log.info("[RocketMQ] init producer, producerGroup:{},namesrvAddr:{}", producerGroup, namesrvAddr);
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setVipChannelEnabled(false);
        producer.setSendMsgTimeout(10000);
        producer.setNamesrvAddr(namesrvAddr);
        rocketMqProxy.setProducer(producer);
        return rocketMqProxy;
    }
}
