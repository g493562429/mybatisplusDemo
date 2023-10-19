package com.gn.demo.service.impl;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/4/27 11:39
 */

import java.util.List;

public interface DemoService {

    <T> void save(List<T> dataList, String type);
 }
