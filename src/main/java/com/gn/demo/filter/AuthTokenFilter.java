//package com.gn.demo.filter;/*
// * @Description:
// * @Author: GuiNing
// * @Date: 2023/4/24 14:24
// */
//
//import com.gn.demo.entity.User;
//import com.gn.demo.service.impl.UserServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.*;
//import java.io.IOException;
//
//@Slf4j
//public class AuthTokenFilter implements Filter {
//
//    @Autowired
//    private UserServiceImpl iUserService;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String username = (String) request.getAttribute("username");
//        String password = (String) request.getAttribute("password");
//
//        // 验证用户是否合法
//        Integer count = iUserService.lambdaQuery().eq(User::getUsername, username)
//                .eq(User::getPassword, password)
//                .count();
//        if (count == 1) {
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
