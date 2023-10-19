package com.gn.demo.service.impl;

import com.gn.demo.entity.Menu;
import com.gn.demo.mapper.MenuMapper;
import com.gn.demo.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public IPage<Menu> findListByPage(Integer pageNum, Integer pageSize){
        IPage<Menu> wherePage = new Page<>(pageNum, pageSize);
        Menu where = new Menu();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Menu menu){
        return baseMapper.insert(menu);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Menu menu){
        return baseMapper.updateById(menu);
    }

    @Override
    public Menu findById(Long id){
        return  baseMapper.selectById(id);
    }

}
