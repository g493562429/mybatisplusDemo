package com.gn.demo.handler;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/4/28 14:19
 */

import java.util.List;

public interface AbstractHandle<T> {

    Object getService(List<T> dataList);

    // 策略模式的运算法则
    Object doSomething(List<T> dataList);
}
