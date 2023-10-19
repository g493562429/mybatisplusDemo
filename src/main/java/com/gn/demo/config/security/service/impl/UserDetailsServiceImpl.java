package com.gn.demo.config.security.service.impl;
/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/8/2 10:58
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gn.demo.config.security.dto.SecurityUser;
import com.gn.demo.entity.Role;
import com.gn.demo.entity.User;
import com.gn.demo.entity.UserRole;
import com.gn.demo.mapper.UserMapper;
import com.gn.demo.service.IRoleService;
import com.gn.demo.service.IUserRoleService;
import com.gn.demo.service.IUserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 根据账号获取用户信息
     *
     * @param username username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中取出用户信息
        List<User> userList = userService.lambdaQuery().eq(User::getUsername, username).list();
        User user;
        //判断用户是否存在
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 返回UserDetails实现类
        return new SecurityUser(user, getUserRoles(user.getId()));
    }

    /**
     * 根据token获取用户权限与基本信息
     *
     * @param token token
     * @return SecurityUser
     */
    public SecurityUser getUserByToken(String token) {
        User user = null;
        List<User> loginList = userService.lambdaQuery().eq(User::getToken, token).list();
        if (!CollectionUtils.isEmpty(loginList)) {
            user = loginList.get(0);
        }
        return user != null ? new SecurityUser(user, getUserRoles(user.getId())) : null;
    }

    /**
     * 根据用户Id获取角色权限信息
     *
     * @param id
     * @return
     */
    private List<Role> getUserRoles(Long id) {
        List<UserRole> userRoles = userRoleService.lambdaQuery().eq(UserRole::getUserId, id).list();
        List<Role> roleList = Lists.newLinkedList();
        for (UserRole userRole : userRoles) {
            Role role = roleService.findById(userRole.getRoleId());
            roleList.add(role);
        }
        return roleList;
    }
}
