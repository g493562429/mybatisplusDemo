package com.gn.demo.threads;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author sarlin_gn
 * @Date 2021/12/8 11:51
 * @Desc
 */
@Slf4j
public class ThreadC {
    public static void test() throws Exception {
        List<Integer> nums = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            nums.add(i);
        }
        log.info("nums.size:{}", nums.size());
//        int[] nums ={1,2,3,4,5,6,7,8,9};
        long start = System.currentTimeMillis();

//        ExecutorService executors = Executors.newCachedThreadPool();
        ExecutorService executors = new ThreadPoolExecutor(1, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        List<Future> resultList = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            Future<String> res = executors.submit(new MyCallable(num));
            resultList.add(res);
        }
        for (Future future : resultList) {
            System.out.println("sorted result " + future.get());
        }
        executors.shutdown();
        log.info("cast.time:{} ms", (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
