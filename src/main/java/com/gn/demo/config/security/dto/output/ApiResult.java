package com.gn.demo.config.security.dto.output;/*
 * @Description: API返回参数
 * @Author: GuiNing
 * @Date: 2023/9/5 12:24
 */

import com.gn.demo.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "API返回参数")
public class ApiResult {
    @ApiModelProperty(value = "响应码", required = true)
    private int code;

    @ApiModelProperty(value = "响应消息", required = false)
    private String message;


    @ApiModelProperty(value = "响应中的数据", required = false)
    private Object data;

    public ApiResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult() {
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ApiResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static ApiResult expired(String message) {
        return new ApiResult(ResultCode.UN_LOGIN.getCode(), message, null);
    }

    public static ApiResult fail(String message) {
        return new ApiResult(ResultCode.FAILURE.getCode(), message, null);
    }

    public static ApiResult fail(Integer code, String message) {
        return new ApiResult(code, message, null);
    }

    public static ApiResult ok(String message) {
        return new ApiResult(ResultCode.SUCCESS.getCode(), message, null);
    }

    public static ApiResult ok(String message, Object data) {
        return new ApiResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static ApiResult ok(Integer code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult ok() {
        return new ApiResult(ResultCode.SUCCESS.getCode(), "OK", null);
    }


}
