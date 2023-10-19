//package com.gn.demo.service.impl;/*
// * @Description:
// * @Author: GuiNing
// * @Date: 2023/4/27 11:11
// */
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.gn.demo.Utils.SpringContextUtil;
//import com.gn.demo.enums.ServiceEnum;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
//@Service
//public class DemoServiceImpl<T> implements DemoService {
//
//    @Override
//    public <T> void save(List<T> dataList, String type) {
//        if (CollectionUtils.isEmpty(dataList)) {
//            return;
//        }
//        String aClass = dataList.get(0).getClass().getName();
//
//        Class<T> service = ServiceEnum.DEMO_DATA_SERVICE.getService();
//        IService<T> bean = (IService<T>) SpringContextUtil.getBean(service);
//        bean.saveBatch(dataList);
//    }
//}
