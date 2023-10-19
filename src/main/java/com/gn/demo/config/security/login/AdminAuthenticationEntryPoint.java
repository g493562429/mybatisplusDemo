package com.gn.demo.config.security.login;/*
 * @Description: 认证权限入口 - 未登录的情况下访问所有接口都会拦截到此
 * @Author: GuiNing
 * @Date: 2023/9/5 11:52
 */

import com.gn.demo.config.security.dto.output.ApiResult;
import com.gn.demo.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //未登录 或 token过期
        if (e != null) {
            ResponseUtils.out(response, ApiResult.expired(e.getMessage()));
        } else {
            ResponseUtils.out(response, ApiResult.expired("jwtToken过期"));
        }
    }
}
