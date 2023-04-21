package com.gn.demo.threads;

import com.gn.demo.service.MsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author sarlin_gn
 * @Date 2021/6/16 16:16
 * @Desc
 */
@Slf4j
public class ThreadB extends Thread{
    private MsService service;
    private RedisTemplate<String,Object> redisTemplate;
    private String key;

    public ThreadB(MsService service,RedisTemplate<String,Object> redisTemplate,String key) {
        this.service = service;
        this.redisTemplate=redisTemplate;
        this.key=key;
    }

    @Override
    public void run() {
        try {
            log.info("开抢：{}", this.getName());
            service.seckill(redisTemplate, key);
            log.info("结束：{}", this.getName());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
