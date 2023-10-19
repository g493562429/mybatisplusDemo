package com.gn.demo.controller;

import com.gn.demo.config.security.constant.Constant;
import com.gn.demo.config.security.dto.output.ApiResult;
import com.gn.demo.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/10/17 16:56
 */
@Slf4j
@RestController
@Api(tags = "登录注销")
public class IndexController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "登录系统", httpMethod = "POST", response = ApiResult.class)
    public ApiResult login() {
        log.info("登录中");
        return ApiResult.ok("登录系统成功", null);
    }


    @GetMapping(value = "/logout", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "注销系统", httpMethod = "GET", response = ApiResult.class)
    public ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(Constant.REQUEST_HEADER);
        log.info("/logout, 获取请求头的token={}", token);
        Claims claims = jwtTokenUtil.getClaimsFromToken(token);
        if (claims == null) {
            //TOKEN已过期，直接返回注销系统成功。目标是注销系统（退出系统登录）
            //直接抛出异常提示，用户原本是想退出系统，TOKEN已经过期，让用户重新登录系统再退出，用户体验不好
            return ApiResult.ok("注销系统成功", null);
        }

        String userId = jwtTokenUtil.getUserId(token);
        if (!StringUtils.isEmpty(token) && !"undefined".equals(token)) {
            //检查token
            redisTemplate.delete(Constant.ADMIN_ACCESS_TOKEN + userId);
        }
        return ApiResult.ok("注销系统成功", null);

    }

}
