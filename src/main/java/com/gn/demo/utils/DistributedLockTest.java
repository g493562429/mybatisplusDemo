package com.gn.demo.utils;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DistributedLockTest {
    private static JedisCluster jedisCluster;

    static {
        // 配置 Redis 集群节点的地址和端口号
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 7000));
        nodes.add(new HostAndPort("127.0.0.1", 7001));
        nodes.add(new HostAndPort("127.0.0.1", 7002));
        nodes.add(new HostAndPort("127.0.0.1", 7003));
        nodes.add(new HostAndPort("127.0.0.1", 7004));
        nodes.add(new HostAndPort("127.0.0.1", 7005));

        // 配置 Jedis 连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(10000);

        // 创建 Jedis Cluster
        jedisCluster = new JedisCluster(nodes, jedisPoolConfig);
    }

    public static void main(String[] args) {
        // 获取分布式锁
        DistributedLock lock = new DistributedLock(jedisCluster, "lock");
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
        // Redisson 锁对象
        private RLock lock;

        /**
         * 构造函数
         * @param jedis JedisCluster 实例
         * @param lockName 锁名称
         */
        public DistributedLock(JedisCluster jedis, String lockName) {
            RedissonClient redisson = Redisson.create();
            this.lock = redisson.getLock(lockName);
        }

        /**
         * 加锁
         * @param waitTime 最长等待时间（毫秒）
         * @param leaseTime 持有锁的时间（毫秒）
         * @return 是否获取到锁
         */
        public boolean tryLock(long waitTime, long leaseTime) {
            try {
                return lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 解锁
         */
        public void unlock() {
            lock.unlockAsync();
        }
    }
}