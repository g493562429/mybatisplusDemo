package com.gn.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.demo.entity.User;

import java.util.List;

/**
 * 用户表 服务类
 *
 * @author gn
 * @since 2021-05-08
 */
public interface IUserService extends IService<User> {

    /**
     * 查询用户表分页数据
     *
     * @param id  分布式锁id
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<User>
     */
    IPage<User> findListByPage(String id, Integer pageNum, Integer pageSize);

    /**
     * 添加用户表
     *
     * @param user 用户表
     * @return int
     */
    int add(User user);

    /**
     * 删除用户表
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改用户表
     *
     * @param user 用户表
     * @return int
     */
    int updateData(User user);

    /**
     * id查询数据
     *
     * @param id id
     * @return User
     */
    User findById(Long id);

    boolean authenticateUser(User user);

    List<User> getUsers();
}
