package com.gn.demo.enums;/*
 * @Description: 响应码枚举
 * @Author: GuiNing
 * @Date: 2023/9/5 12:29
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public enum ResultCode {

    SUCCESS(200,"请求成功"),

    FAILURE(400,"请求失败"),

    UN_LOGIN(401,"未登录"),

    UNAUTHORIZED(403, "用户名或密码不正确");

    private int code;
    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
