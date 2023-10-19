package com.gn.demo.enums;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/4/27 11:53
 */

import com.gn.demo.service.IDemoDataService;

import java.util.Objects;

public enum ServiceEnum {


    DEMO_DATA_SERVICE("DemoData", IDemoDataService.class);

    /**
     * 实体类
     */
    private String className;
    /**
     * 服务类
     */
    private Class service;


    ServiceEnum(String className, Class service) {
        this.className = className;
        this.service = service;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class getService() {
        return service;
    }

    public void setService(Class service) {
        this.service = service;
    }

    public static Class getServiceByClassName(String className) {
        for (ServiceEnum serviceEnum : ServiceEnum.values()) {
            if (Objects.equals(serviceEnum.getClassName(), className)) {
                return serviceEnum.getService();
            }
        }
        return null;
    }

}
