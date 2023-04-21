package com.gn.demo.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * redis分布式锁的实现
 *
 * @author sarlin_gn
 * @date 2021/06/01
 */
@Component
public class DisLockUtil {

    @Resource
    private JedisPool jedisPool;

    private static final int DEFAULT_EXPIRE_TIME = 5;

    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * 释放锁
     *
     * @param key key
     * @return boolean
     */
    public final boolean unlock(String key, boolean needCheck) {
        boolean result = false;
        try (Jedis jedis = jedisPool.getResource()) {
            if (needCheck) {
                String expireTimeCache = jedis.get(key);
                // 判断锁是否过期了
                if (StringUtils.isBlank(expireTimeCache)) {
                    result = true;
                }
                if (System.currentTimeMillis() - Long.parseLong(expireTimeCache) > 0) {
                    // 直接删除
                    jedis.del(key);
                    result = true;
                }
            } else {
                jedis.del(key);
            }
        }
        return result;
    }

    /**
     * 获取分布式锁
     *
     * @param key 锁key
     * @param expireSecond 过期时间
     * @return boolean
     */
    public final boolean lock(String key, int expireSecond) {
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("传入的key为空");
        }
        expireSecond = expireSecond == 0 ? DEFAULT_EXPIRE_TIME : expireSecond;
        // 过期的时候的时间戳
        long expireTime = System.currentTimeMillis() + expireSecond * 1000L + 1;
        boolean setResult = false;
        try (Jedis jedis = jedisPool.getResource()) {
            if (jedis.setnx(key, String.valueOf(expireTime)) == 1) {
                // 说明加锁成功
                setResult = true;
            }
            if (jedis.ttl(key) < 0) {
                jedis.expire(key, expireSecond);
            }
            if (setResult) {
                return true;
            }
            String expireTimeCache = jedis.get(key);
            System.out.println(expireTimeCache + "====" + jedis.ttl(key) + ", now:" + System.currentTimeMillis());
            // 判断锁是否过期了
            if (StringUtils.isNotBlank(expireTimeCache) && System.currentTimeMillis() - Long.parseLong(expireTimeCache) > 0) {
                String oldExpireTime = jedis.getSet(key, String.valueOf(expireTime));
                if (StringUtils.isNotBlank(oldExpireTime) && oldExpireTime.equals(String.valueOf(expireTime))) {
                    jedis.expire(key, expireSecond);
                    setResult = true;
                }
            }
        }
        return setResult;
    }

}