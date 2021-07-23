package com.gn.demo.rocketmq.config;

/**
 * @Author sarlin_gn
 * @Date 2021/7/20 17:15
 * @Desc
 */
public interface MqProxy {
    boolean sendMessage(String content, String logEntityTag, String logType);

    void syncSendMsg(String content, String logEntityTag, String logType);
}
