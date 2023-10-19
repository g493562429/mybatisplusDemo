package com.gn.demo.config.security.login;

import com.gn.demo.config.security.dto.output.ApiResult;
import com.gn.demo.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Description: 认证失败处理 - 前后端分离情况下返回json数据格式
 * @Author: GuiNing
 * @Date: 2023/9/20 16:00
 */
@Slf4j
@Component
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ApiResult result;
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            result = ApiResult.fail(e.getMessage());
        } else if (e instanceof LockedException) {
            result = ApiResult.fail("账户被锁定，请联系管理员！");
        } else if (e instanceof CredentialsExpiredException) {
            result = ApiResult.fail("证书过期，请联系管理员！");
        } else if (e instanceof AccountExpiredException) {
            result = ApiResult.fail("账户过期，请联系管理员！");
        } else if (e instanceof DisabledException) {
            result = ApiResult.fail("账户被禁用，请联系管理员！");
        } else {
            log.error("登录失败:", e);
            result = ApiResult.fail("登录失败!");
        }
        ResponseUtils.out(response, result);
    }
}
