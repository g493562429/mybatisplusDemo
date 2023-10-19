package com.gn.demo.config.security.login;

import com.gn.demo.config.security.dto.SecurityUser;
import com.gn.demo.config.security.dto.output.ApiResult;
import com.gn.demo.entity.User;
import com.gn.demo.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Description: 认证成功处理
 * @Author: GuiNing
 * @Date: 2023/9/20 15:54
 */
@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        User user = new User();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        user.setToken(securityUser.getCurrentUserInfo().getToken());
        user.setUpdatePasswordTime(securityUser.getCurrentUserInfo().getUpdatePasswordTime());
        ResponseUtils.out(response, ApiResult.ok("登录成功!", user));
    }
}
