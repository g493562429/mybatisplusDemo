package com.gn.demo.handler;

import com.gn.demo.config.security.dto.output.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @Description: 全局异常处理
 * @Author: GuiNing
 * @Date: 2023/10/17 15:31
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String msg = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                msg = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return ApiResult.fail(msg);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResult handle(Exception e) {
        log.error("全局异常捕获：", e);
        return ApiResult.fail(e.getMessage());
    }

}
