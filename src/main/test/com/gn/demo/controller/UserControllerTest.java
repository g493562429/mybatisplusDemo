package com.gn.demo.controller;

import com.gn.demo.service.IUserService;
import com.gn.demo.service.MsService;
import com.gn.demo.threads.MyDisLockThread;
import com.gn.demo.threads.ThreadB;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserControllerTest extends TestCase {

    @Resource
    private IUserService iUserService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void getByIdTest(){
        for (long i = 1; i < 6; i++) {
            MyDisLockThread thread = new MyDisLockThread(i, "myKey", iUserService);
            thread.start();
            log.info("i=" + i);
//            Object t = thread.getT();
//            log.info("t:{}", JSON.toJSONString(t));
        }
    }

    @Test
    public void testSys() throws Exception{
        MsService service = new MsService();
        for (int i = 0; i < 10; i++) {
            ThreadB threadA = new ThreadB(service, redisTemplate, "MSKEY");
            threadA.start();
        }

    }

}