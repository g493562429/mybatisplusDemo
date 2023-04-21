package com.gn.demo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class XSSFilter implements Filter {
 
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  (HttpServletRequest)servletRequest;
        log.info("过滤开始.....");
        filterChain.doFilter(new XSSRequestWrapper(request) , servletResponse);
    }
 
    @Override
    public void destroy() {
 
    }
}