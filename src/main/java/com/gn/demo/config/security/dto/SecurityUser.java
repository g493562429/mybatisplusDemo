package com.gn.demo.config.security.dto;
/*
 * @Description: 安全认证用户详情
 * @Author: GuiNing
 * @Date: 2023/8/2 11:04
 */

import com.gn.demo.entity.Role;
import com.gn.demo.entity.User;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

@Data
@Slf4j
public class SecurityUser implements UserDetails {

    /**
     * 当前登录用户
     */
    private transient User currentUserInfo;

    /**
     * 角色
     */
    private transient List<Role> roleList;

    /**
     * 当前用户所拥有的角色代码
     */
    private transient String roleCodes;

    public SecurityUser() {
    }

    public SecurityUser(User user) {
        this.currentUserInfo = user;
    }

    public SecurityUser(User user, List<Role> roleList) {
        if (user != null) {
            this.currentUserInfo = user;
            this.roleList = roleList;
            if (!CollectionUtils.isEmpty(roleList)) {
                StringJoiner roleCodes = new StringJoiner(",", "[", "]");
                roleList.forEach(e -> roleCodes.add(e.getCode()));
                this.roleCodes = roleCodes.toString();
            }
        }

    }

    /**
     * 获取当前用户所具有的角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(this.roleList)) {
            for (Role role : this.roleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getCode());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUserInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
