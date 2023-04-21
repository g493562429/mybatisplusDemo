package com.gn.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁的注解, 通过指定key作为分布式锁的key
 *
 * @author sarlin_gn on 2021/06/01
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DisLock {

    /**
     * 分布式锁的key
     *
     * @return String
     */
    String key();

    /**
     * 分布式锁用的业务场景id
     *
     * @return String
     */
    String biz();

    /**
     * 过期时间, 默认是5秒
     * 单位是秒
     *
     * @return int
     */
    int expireTime() default 5;

}