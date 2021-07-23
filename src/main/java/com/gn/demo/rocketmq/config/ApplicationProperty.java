package com.gn.demo.rocketmq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 16:55
 * @Desc
 */
@ConfigurationProperties(prefix = "gn.ceshi.mq")
@Data
public class ApplicationProperty {
    private String mqType;
    private String topic;
    private Integer tryCount = 3;
    private ApplicationProperty.RocketMQProperty rocketmq = new ApplicationProperty.RocketMQProperty();

    @Data
    public static class RocketMQProperty{
        @Value("gn.ceshi.mq")
        private String producerGroup;
        private String namesvrAddr;
    }
}
