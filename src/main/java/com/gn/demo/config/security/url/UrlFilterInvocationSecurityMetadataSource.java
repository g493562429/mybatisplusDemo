package com.gn.demo.config.security.url;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gn.demo.config.security.MyProperties;
import com.gn.demo.config.security.constant.Constant;
import com.gn.demo.entity.Menu;
import com.gn.demo.entity.Role;
import com.gn.demo.entity.RoleMenu;
import com.gn.demo.mapper.MenuMapper;
import com.gn.demo.mapper.RoleMapper;
import com.gn.demo.mapper.RoleMenuMapper;
import com.google.common.collect.Lists;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/*
 * @Description: 获取访问该url所需要的用户角色权限信息
 * @Author: GuiNing
 * @Date: 2023/9/20 16:27
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    MyProperties myProperties;

    /**
     * 返回该url所需要的用户权限信息
     *
     * @param object 储存请求url信息
     * @return null:表示不需要任何权限都可以访问
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取当前请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //todo 忽略url请放在此处进行过滤放行 equals需优化,支持/**样式
        for (String ignoreUrl : myProperties.getAuth().getIgnoreUrls()) {
            if (ignoreUrl.equals(requestUrl)) {
                return null;
            }
        }

        if (requestUrl.contains("/login") || requestUrl.contains("/groupChat")) {
            return null;
        }

        //数据库中所有url
        List<Menu> permissionList = menuMapper.selectList(null);
        for (Menu permission : permissionList) {
            //获取该url所对应的权限
            if (("/api" + permission.getUrl()).equals(requestUrl)) {
                List<RoleMenu> permissions = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("menu_id", permission.getId()));
                List<String> roles = Lists.newLinkedList();
                if (!CollectionUtils.isEmpty(permissions)) {
                    permissions.forEach(e -> {
                        Long roleId = e.getRoleId();
                        Role role = roleMapper.selectById(roleId);
                        roles.add(role.getCode());
                    });
                }
                // 保存该url对应角色权限信息
                return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
            }
        }
        //如果数据中没有找到相应url资源则为非法访问，要求用户登录再进行操作
        return SecurityConfig.createList(Constant.ROLE_LOGIN);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
