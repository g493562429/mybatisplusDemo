package com.gn.demo.entity;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author sarlin_gn
 * @Date 2021/5/12 15:15
 * @Desc
 */
@Slf4j
public class Person {

    private String name;

    private int age;

    public Person(String name, int age) {
        log.info("进来了创建了 " + name + "Person");
        this.name = name;
        this.age = age;
    }
}
