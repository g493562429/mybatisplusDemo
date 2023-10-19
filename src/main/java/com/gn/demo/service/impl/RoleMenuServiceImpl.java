package com.gn.demo.service.impl;

import com.gn.demo.entity.RoleMenu;
import com.gn.demo.mapper.RoleMenuMapper;
import com.gn.demo.service.IRoleMenuService;
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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public IPage<RoleMenu> findListByPage(Integer pageNum, Integer pageSize){
        IPage<RoleMenu> wherePage = new Page<>(pageNum, pageSize);
        RoleMenu where = new RoleMenu();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(RoleMenu roleMenu){
        return baseMapper.insert(roleMenu);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(RoleMenu roleMenu){
        return baseMapper.updateById(roleMenu);
    }

    @Override
    public RoleMenu findById(Long id){
        return  baseMapper.selectById(id);
    }

}
