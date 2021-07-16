package com.gn.demo.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Redisson操作redis
 * Redisson除了提供同步接口外，还提供了异步（Async）、反射式（Reactive）和RxJava2标准的接口。
 * Redisson会序列化java对象然后保存到redis，所以通过redis命令行设置的值，Redisson来获取值会报错；通redis命令行获取Redisson设置的值前面会多出序列化相关的信息
 */
public class RedissonCase {
    private RedissonClient client;
    private RedissonReactiveClient reactiveClient;
    private RedissonRxClient rxClient;

    @Before
    public void before() {
        Config config = new Config();
        //config.setCodec(new org.redisson.client.codec.StringCodec());
        config.useSingleServer().setAddress("redis://10.49.196.10:6379").setPassword("123456");
        client = Redisson.create(config);
        reactiveClient = Redisson.createReactive(config);
        rxClient = Redisson.createRx(config);
    }

    @After
    public void after() {
        client.shutdown();
        reactiveClient.shutdown();
        rxClient.shutdown();
    }

    /**
     * 通用对象桶，可以用来存放任类型的对象
     */
    @Test
    public void bucket() throws Exception {
        //同步
        RBucket<String> bucket = client.getBucket("name");
        bucket.set("zhaoyun");
        System.out.println(bucket.get());

        //异步
        RBucket<String> bucket2 = client.getBucket("name2");
        bucket2.setAsync("赵云2").get();
        bucket2.getAsync().thenAccept(System.out::println);

        //Reactive
        RBucketReactive<String> bucket3 = reactiveClient.getBucket("name3");
        bucket3.set("赵云3").block();
        bucket3.get().subscribe(System.out::println);

        //RxJava2
        RBucketRx<String> bucket4 = rxClient.getBucket("name4");
        bucket4.set("赵云4").blockingGet();
        bucket4.get().subscribe(System.out::println);

        Thread.sleep(1000 * 5);
    }

    /**
     * 二进制流
     * 提供了InputStream接口和OutputStream接口的实现
     */
    @Test
    public void stream() throws Exception {
        RBinaryStream stream = client.getBinaryStream("stream");
        stream.set("赵云".getBytes());
        OutputStream outputStream = stream.getOutputStream();
        outputStream.write("张飞".getBytes());

        InputStream inputStream = stream.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b)) != -1) {
            result.write(b, 0, len);
        }
        System.out.println(result.toString());
    }

    @Test
    public void atomicLong() {
        RAtomicLong atomicLong = client.getAtomicLong("atomicLong");
        atomicLong.set(10);
        atomicLong.incrementAndGet();
        System.out.println(atomicLong);
    }

    /**
     * 限流器
     */
    @Test
    public void rateLimiter() throws InterruptedException {
        RRateLimiter rateLimiter = client.getRateLimiter("rateLimiter");
        //初始化 最大流速:每1秒钟产生5个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 5, 1, RateIntervalUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                int i = 0;
                @Override
                public void run() {
                    while(true) {
                        rateLimiter.acquire(1);
                        System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-" + i++);
                    }
                }
            }).start();
        }

        Thread.sleep(1000 * 5);
    }

    /**
     * RList实现了java.util.List接口
     */
    @Test
    public void list() {
        RList<String> list = client.getList("list");
        list.add("a");
        list.add("赵云");
        list.add("张飞");
        list.remove(1);
        System.out.println(list);
    }

    /**
     * RMap实现了java.util.concurrent.ConcurrentMap接口和java.util.Map接口
     * @throws Exception
     */
    @Test
    public void map() throws Exception {
        RMap<String, String> map = client.getMap("map");
        map.put("name", "赵云");
        map.put("location", "常山");
        map.put("camp", "蜀");
        map.remove("location");
        map.forEach((key, value) -> {System.out.println("key=" + key + ",value=" + value);});
    }

    /**
     * RSet实现了java.util.Set接口
     * @throws Exception
     */
    @Test
    public void set() {
        RSet<String> set = client.getSet("set");
        set.add("赵云");
        set.add("张飞");
        set.forEach(System.out::println);
    }

    /**
     * RQueue实现了java.util.Queue接口
     */
    @Test
    public void queue() {
        RQueue<String> queue = client.getQueue("queue");
        queue.add("赵云");
        queue.add("张飞");
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    /**
     * 可重入锁 RLock实现了java.util.concurrent.locks.Lock接口
     */
    @Test
    public void lock() throws InterruptedException {
        RLock lock = client.getLock("lock");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-" + "获取了锁");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
        Thread.sleep(1000 * 5);
    }

    /**
     * Redisson的分布式RBitSetJava对象采用了与java.util.BiteSet类似结构的设计风格
     */
    @Test
    public void bitSet() {
        RBitSet bitSet = client.getBitSet("bs");
        bitSet.expire(5, TimeUnit.DAYS);
        bitSet.set(0, true);
        bitSet.set(20, true);
        bitSet.set(96, true);
        System.out.println(bitSet.get(10));
        System.out.println(bitSet.get(20));
    }

    /**
     * Redisson利用Redis实现了Java分布式布隆过滤器（Bloom Filter）
     */
    @Test
    public void bf() {
        RBloomFilter<String> bf = client.getBloomFilter("qq");
        if (!bf.isExists()) {
            bf.tryInit(150000000L, 0.05);
            bf.add("test");
            bf.expire(200, TimeUnit.SECONDS);
        }
        bf.add("https://www.baidu.com/");
        bf.add("https://www.tmall.com/");
        bf.add("https://www.jd.com/");
        System.out.println(bf.contains("https://www.tmall.com/"));
        System.out.println(bf.count());
    }
}