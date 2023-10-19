package com.gn.demo.service.impl;

import com.gn.demo.entity.UserRole;
import com.gn.demo.mapper.UserRoleMapper;
import com.gn.demo.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 *  服务实现类
 *
 * @author gn
 * @since 2023-08-02
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public IPage<UserRole> findListByPage(Integer pageNum, Integer pageSize){
        IPage<UserRole> wherePage = new Page<>(pageNum, pageSize);
        UserRole where = new UserRole();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(UserRole userRole){
        return baseMapper.insert(userRole);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(UserRole userRole){
        return baseMapper.updateById(userRole);
    }

    @Override
    public UserRole findById(Long id){
        return  baseMapper.selectById(id);
    }

}
