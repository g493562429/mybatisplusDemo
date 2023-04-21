package com.gn.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.demo.annotation.DisLock;
import com.gn.demo.entity.User;
import com.gn.demo.mapper.UserMapper;
import com.gn.demo.service.IUserService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户表 服务实现类
 *
 * @author gn
 * @since 2021-05-08
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @DisLock(key = "#id", biz = "用户查询")
    @Override
    public IPage<User> findListByPage(String id, Integer pageNum, Integer pageSize) {
        IPage<User> wherePage = new Page<>(pageNum, pageSize);
        User where = User.builder().build();
        log.info("findListByPage.start...id : {}", id);
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(User user) {
        return baseMapper.insert(user);
    }

    @Override
    public int delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(User user) {
        return baseMapper.updateById(user);
    }

    @Override
    @DisLock(key = "#id", biz = "findById")
    public User findById(Long id) {
        log.info("findById.id:{}", id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return baseMapper.selectById(id);
    }

    @Override
    public boolean authenticateUser(User user) {
        // 验证用户是否合法
        Integer count = lambdaQuery().eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .count();
        return count == 1;
    }

    @Override
    public List<User> getUsers() {
        return query().list();
    }

}
