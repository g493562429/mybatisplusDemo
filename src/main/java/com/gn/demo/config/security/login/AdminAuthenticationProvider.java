package com.gn.demo.config.security.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gn.demo.config.security.constant.Constant;
import com.gn.demo.config.security.dto.SecurityUser;
import com.gn.demo.config.security.service.impl.UserDetailsServiceImpl;
import com.gn.demo.entity.Role;
import com.gn.demo.entity.User;
import com.gn.demo.entity.UserRole;
import com.gn.demo.mapper.RoleMapper;
import com.gn.demo.mapper.UserMapper;
import com.gn.demo.mapper.UserRoleMapper;
import com.gn.demo.utils.JwtTokenUtil;
import com.gn.demo.utils.PasswordUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*
 * @Description: 自定义认证处理
 * @Author: GuiNing
 * @Date: 2023/9/5 15:09
 */
@Component
@Slf4j
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String psd = (String) authentication.getCredentials();
        User user;
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("username", userName));
        //判断用户是否存在
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名不存在");
        }

        //新增判断是否锁定状态
        Object count = redisTemplate.opsForValue().get(Constant.ACCOUNT_LOCK_KEY + userName);
        log.info("密码错误{}次", count);
        if (count != null) {
            Integer updateCount = Integer.valueOf(String.valueOf(count));
            if (updateCount >= 3) {
                throw new LockedException("密码错误超出限制次数，锁定2个小时");
            }
        }

        List<UserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        List<Role> roleList = Lists.newLinkedList();
        for (UserRole userRole : userRoles) {
            Role role = roleMapper.selectById(userRole.getRoleId());
            roleList.add(role);
        }

//        SecurityUser userInfo = new SecurityUser(user, roleList);

        SecurityUser userInfo = (SecurityUser) userDetailsService.loadUserByUsername(userName);
        boolean isValid = PasswordUtils.isValidPassword(psd, userInfo.getPassword(), userInfo.getCurrentUserInfo().getSalt());
        //验证密码
        if (!isValid) {
            //设置密码错误次数
            if (count != null) {
                Integer updateCount = Integer.valueOf(String.valueOf(count));
                updateCount++;
                redisTemplate.opsForValue().set(Constant.ACCOUNT_LOCK_KEY + userInfo.getUsername(), updateCount, 2 * 60 * 60, TimeUnit.MINUTES);
                ;
            } else {
                redisTemplate.opsForValue().set(Constant.ACCOUNT_LOCK_KEY + userName, 1, 2 * 60 * 60, TimeUnit.MINUTES);
            }
            throw new LockedException("密码错误");
        }

        // 前后端分离，前端传过来的密码是加密的，所以需要解密
        // 更新登录令牌
        String token = PasswordUtils.encodePassword(String.valueOf(System.currentTimeMillis()), Constant.SALT);
        // 当前用户所拥有的角色代码
        String roleCodes = userInfo.getRoleCodes();
        //生成jwt访问令牌
        String jwt = Jwts.builder()
                //用户角色
                .claim(Constant.ROLE_LOGIN, roleCodes)
                //用户名
                .setSubject(authentication.getName())
                //过期时间-2小时
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60))
                // 加密算法和密钥
                .signWith(SignatureAlgorithm.HS512, Constant.SALT)
                .compact();
        String userId = String.valueOf(userInfo.getCurrentUserInfo().getId());

        Map<String, Object> claims = Maps.newHashMap();
        Collection<GrantedAuthority> authorities = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(roleList)) {
            for (Role role : roleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getCode());
                authorities.add(authority);
            }
        }

        claims.put(Constant.JWT_USER, user);
        claims.put(Constant.JWT_ROLE_LIST, roleList);
        claims.put(Constant.JWT_USER_NAME, userInfo.getCurrentUserInfo().getUsername());
        String accessToken = jwtTokenUtil.getAccessToken(userId, claims);
        log.debug("username={}", userInfo.getCurrentUserInfo().getUsername());
        log.debug("系统生成jwt={}", jwt);
        log.debug("userId={}", userId);
        log.debug("系统生成访问token={}", token);

        user.setToken(token);
        userMapper.updateById(user);
        userInfo.getCurrentUserInfo().setToken(accessToken);

        redisTemplate.opsForValue().set(Constant.ADMIN_ACCESS_TOKEN + userId, accessToken, 2 * 60 * 60, TimeUnit.SECONDS);

        return new UsernamePasswordAuthenticationToken(userInfo, psd, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
