package com.gn.demo.aops;

import com.gn.demo.annotation.DisLock;
import com.gn.demo.enums.ErrorCodeEnum;
import com.gn.demo.exception.CustomException;
import com.gn.demo.utils.CacheKeyParser;
import com.gn.demo.utils.DisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 处理@DisLock注解的切面
 *
 * @author sarlin_gn on 2021/06/01
 * @version 1.0
 */
@Aspect
@Order(value = 1)
@Component
@Slf4j
public class DisLockAspect {

    @Resource
    private DisLockUtil disLockUtil;

    private static final int MIN_EXPIRE_TIME = 3;

    @Around(value = "@annotation(disLock)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint, DisLock disLock) throws Throwable {
        int expireTIme = Math.max(disLock.expireTime(), MIN_EXPIRE_TIME);
        String disKey = CacheKeyParser.parse(proceedingJoinPoint, disLock.key(), disLock.biz());
        log.info("disKey:{}", disKey);
        boolean lock = disLockUtil.lock(disKey, expireTIme);
        int count = 1;
        while (!lock && count < MIN_EXPIRE_TIME) {
            lock = disLockUtil.lock(disKey, expireTIme);
            count++;
            TimeUnit.SECONDS.sleep(1);
        }
        Object proceed;
        if (lock) {
            // 允许查询
            try {
                proceed = proceedingJoinPoint.proceed();
            } finally {
                // 删除分布式锁
                disLockUtil.unlock(disKey, false);
            }
        } else {
            throw new CustomException(ErrorCodeEnum.DUPLICATE_REQUEST.getMessage());
        }
        return proceed;
    }

}