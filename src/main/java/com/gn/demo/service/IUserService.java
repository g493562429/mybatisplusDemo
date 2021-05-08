package com.gn.demo.service;

import com.gn.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<User>
     */
    IPage<User> findListByPage(Integer pageNum, Integer pageSize);

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
}
