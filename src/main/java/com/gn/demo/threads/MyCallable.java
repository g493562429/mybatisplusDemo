package com.gn.demo.threads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @Author sarlin_gn
 * @Date 2021/12/8 15:58
 * @Desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MyCallable implements Callable<String> {

    private int num;

    @Override
    public String call() throws Exception {
        int res = num*num;
        System.out.println("exe:"+ res);
        return "result = "+res;
    }
}
