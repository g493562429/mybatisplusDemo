package com.gn.demo.config.security.url;

import com.gn.demo.config.security.dto.output.ApiResult;
import com.gn.demo.enums.ResultCode;
import com.gn.demo.utils.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Description: 认证url权限 - 登录后访问接口无权限 - 自定义403无权限相应内容
 * 登录过后的权限处理【注:要和未登录时的权限处理区分开】
 * @Author: GuiNing
 * @Date: 2023/10/17 14:36
 */
@Component
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtils.out(response, ApiResult.fail(ResultCode.UNAUTHORIZED.getCode(), e.getMessage()));
    }
}
