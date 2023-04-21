package com.gn.demo.threads;

import com.gn.demo.entity.User;
import com.gn.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author sarlin_gn
 * @Date 2021/6/1 17:27
 * @Desc
 */
@Slf4j
public class MyDisLockThread extends Thread {

    private Long id;

    private String name;

    private Object t;

    @Override
    public void run() {
        IUserService iUserService = (IUserService) t;
        User byId = iUserService.findById(1L);
        log.info("user.byId:{}", byId.getId());
    }

    public MyDisLockThread(Long id, String name, Object t){
        this.id = id;
        this.name = name;
        this.t = t;
    }

}
