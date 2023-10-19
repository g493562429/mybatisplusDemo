package com.gn.demo.config.security.filter;
/*
 * @Description: 访问鉴权 - 每次访问接口都会经过这
 * @Author: GuiNing
 * @Date: 2023/8/2 10:53
 */

import com.alibaba.fastjson.JSON;
import com.gn.demo.config.security.constant.Constant;
import com.gn.demo.config.security.dto.SecurityUser;
import com.gn.demo.config.security.login.AdminAuthenticationEntryPoint;
import com.gn.demo.config.security.service.impl.UserDetailsServiceImpl;
import com.gn.demo.config.security.utils.MultiReadHttpServletRequest;
import com.gn.demo.config.security.utils.MultiReadHttpServletResponse;
import com.gn.demo.entity.Role;
import com.gn.demo.entity.User;
import com.gn.demo.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AdminAuthenticationEntryPoint authenticationEntryPoint;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    protected MyAuthenticationFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("请求头类型:{}", request.getContentType());
        if (request.getContentType() == null || request.getContentLength() <= 0 || request.getContentType().contains(Constant.REQUEST_HEADERS_CONTENT_TYPE)) {
            MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
            MultiReadHttpServletResponse wrapperResponse = new MultiReadHttpServletResponse(response);
            StopWatch stopWatch = new StopWatch();
            try {
                stopWatch.start();
                //记录请求的消息体
                logRequestBody(wrappedRequest);

                // 前后端分离情况下，前端登录后将token储存在cookie中，每次访问接口时通过token去拿用户权限
                String jwtToken = request.getHeader(Constant.REQUEST_HEADER);
                log.info("后台检查令牌1:{}", jwtToken);
                if (!StringUtils.isEmpty(jwtToken) && !Objects.equals("undefined", jwtToken)) {
                    Map<String, Object> claims = jwtTokenUtil.getClaimsFromToken(jwtToken);
                    if (claims == null) {
                        throw new BadCredentialsException("TOKEN已过期，请重新登录");
                    }
                    //todo 如需使用jwt特性在此做处理
                    Object jwtUser = claims.get(Constant.JWT_USER);
                    Object jwtRoleList = claims.get(Constant.JWT_ROLE_LIST);
                    String userId = jwtTokenUtil.getUserId(jwtToken);
                    log.info("获取当前登录userId1=[{}]", userId);
                    User user = JSON.parseObject(JSON.toJSONString(jwtUser), User.class);
                    List<Role> roleList = JSON.parseArray(JSON.toJSONString(jwtRoleList), Role.class);
                    log.info("jwtUser1=[{}]", user);
                    log.info("jwtRoleList1=[{}]", roleList);

                    SecurityUser securityUser = new SecurityUser(user, roleList);
                    String jwtTokenFromRedis = (String) redisTemplate.opsForValue().get(Constant.ADMIN_ACCESS_TOKEN + userId);
                    if (StringUtils.isEmpty(jwtTokenFromRedis)) {
                        throw new BadCredentialsException("TOKEN已过期，请重新登录");
                    } else {
                        //重新设置redis key 过期时间
                        redisTemplate.opsForValue().set(Constant.ADMIN_ACCESS_TOKEN + userId, jwtToken, 2 * 60 * 60, TimeUnit.SECONDS);
                    }
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                    //全局注入角色权限信息和登录用户基本信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(wrappedRequest, wrapperResponse);
            } catch (ExpiredJwtException e) {
                //jwt令牌过期
                SecurityContextHolder.clearContext();
                this.authenticationEntryPoint.commence(wrappedRequest, wrapperResponse, null);
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                this.authenticationEntryPoint.commence(wrappedRequest, wrapperResponse, e);
            } finally {
                stopWatch.stop();
                long usedTimes = stopWatch.getTotalTimeMillis();
                //记录响应的消息体
                logResponseBody(wrappedRequest, wrapperResponse, usedTimes);
            }
        } else {

            try {
                String jwtToken = request.getHeader(Constant.REQUEST_HEADER);
                log.info("后台检查令牌2:{}", jwtToken);
                if (StringUtils.isEmpty(jwtToken) && !Objects.equals("undefined", jwtToken)) {
                    Map<String, Object> claims = jwtTokenUtil.getClaimsFromToken(jwtToken);
                    if (claims != null) {
                        throw new BadCredentialsException("TOKEN已过期，请重新登录！");
                    }
                    //todo 如需使用jwt特性在此做处理
                    Object jwtUser = claims.get(Constant.JWT_USER);
                    Object jwtRoleList = claims.get(Constant.JWT_ROLE_LIST);
                    String userId = jwtTokenUtil.getUserId(jwtToken);
                    log.info("获取当前登录userId2=[{}]", userId);
                    User user = JSON.parseObject(JSON.toJSONString(jwtUser), User.class);
                    List<Role> roleList = JSON.parseArray(JSON.toJSONString(jwtRoleList), Role.class);
                    log.info("jwtUser2=[{}]", user);
                    log.info("jwtRoleList2=[{}]", roleList);

                    SecurityUser securityUser = new SecurityUser(user, roleList);
                    String jwtTokenFromRedis = (String) redisTemplate.opsForValue().get(Constant.ADMIN_ACCESS_TOKEN + userId);
                    if (StringUtils.isEmpty(jwtTokenFromRedis)) {
                        throw new BadCredentialsException("TOKEN已过期，请重新登录");
                    } else {
                        //重新设置redis key 过期时间
                        redisTemplate.opsForValue().set(Constant.ADMIN_ACCESS_TOKEN + userId, jwtToken, 2 * 60 * 60, TimeUnit.SECONDS);
                    }
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                    //全局注入角色权限信息和登录用户基本信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(request, response);
            } catch (ExpiredJwtException e) {
                //jwt令牌过期
                SecurityContextHolder.clearContext();
                this.authenticationEntryPoint.commence(request, response, null);
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                this.authenticationEntryPoint.commence(request, response, e);
            } finally {
                log.debug("filter过滤权限结束");
            }
        }
    }

    private void logResponseBody(MultiReadHttpServletRequest request, MultiReadHttpServletResponse response, long usedTimes) {
        MultiReadHttpServletResponse wrapper = response;
        if (wrapper != null) {
            byte[] buf = wrapper.getBody();
            if (buf.length > 0) {
                String payload;

                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                log.debug("{} 耗时:{}ms,返回参数:{}", Constant.URL_MAPPING_MAP.get(request.getRequestURI()), usedTimes, payload);
            }
        }

    }

    private String logRequestBody(MultiReadHttpServletRequest request) {
        MultiReadHttpServletRequest wrapper = request;
        if (wrapper != null) {

            try {
                String bodyJson = wrapper.getBodyJsonStrByJson(request);
                String url = wrapper.getRequestURI().replace("//", "/");
                log.debug("请求url:{}", url);
                Constant.URL_MAPPING_MAP.put(url, url);
                log.debug("{} 接收到的参数:{}", url, bodyJson);
                return bodyJson;
            } catch (Exception e) {
                log.error("logRequestBody.fail:", e);
            }
        }
        return null;
    }
}
