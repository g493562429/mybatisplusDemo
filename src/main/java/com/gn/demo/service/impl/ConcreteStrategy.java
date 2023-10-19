package com.gn.demo.service.impl;
/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/4/28 14:15
 */
import com.gn.demo.handler.AbstractHandle;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ConcreteStrategy<T> {

    //抽象service
    private AbstractHandle<T> handle;

    // 封装后的策略方法
    public void doAnything(List<T> dataList){
        this.handle.doSomething(dataList);
    }

}
