package com.gn.demo.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class DistributedLockTest2 {
    private static Jedis jedis;

    static {
        // 配置 Redis 密码
        String host = "127.0.0.1";
        int port = 6379;
        jedis = new Jedis(host, port);
        jedis.auth("password");
    }

    public static void main(String[] args) {
        // 获取分布式锁
        DistributedLock lock = new DistributedLock(jedis, "lock");
        boolean res = lock.tryLock(5000, 10000);
        if (res) {
            try {
                System.out.println("获取到分布式锁，执行业务处理...");
                // 模拟业务处理
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放分布式锁
                lock.unlock();
                System.out.println("释放分布式锁！");
            }
        } else {
            System.out.println("获取分布式锁失败！");
        }
    }

    public static class DistributedLock {
        private static final String LOCK_SUCCESS = "OK";
        private static final String SET_IF_NOT_EXIST = "NX";
        private static final String SET_WITH_EXPIRE_TIME = "PX";

        private Jedis jedis;
        private String lockKey;

        public DistributedLock(Jedis jedis, String lockKey) {
            this.jedis = jedis;
            this.lockKey = lockKey;
        }

        /**
         * 尝试获取分布式锁
         * 
         * @param waitTime 等待时间（毫秒）
         * @param leaseTime 持有锁的时间（毫秒）
         * @return 是否获取到锁
         */
        public boolean tryLock(long waitTime, long leaseTime) {
            long start = System.currentTimeMillis();
            try {
                while (true) {
                    String result = jedis.set(lockKey, "1", SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, leaseTime);
                    if (LOCK_SUCCESS.equals(result)) {
                        return true;
                    }
                    long end = System.currentTimeMillis();
                    if (end - start >= waitTime) {
                        return false;
                    }
                    Thread.sleep(100);
                }
            } catch (JedisConnectionException e) {
                // jedis 连接异常，重试
                return tryLock(waitTime, leaseTime);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 释放分布式锁
         */
        public void unlock() {
            try {
                jedis.del(lockKey);
            } catch (JedisConnectionException e) {
                // jedis 连接异常，重试
                unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}