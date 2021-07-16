package com.gn.demo.service;

import com.gn.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author gn
 * @since 2021-06-28
 */
public interface IUserService extends IService<User> {

    /**
     * 查询分页数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<User>
     */
    IPage<User> findListByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加
     *
     * @param user 
     * @return int
     */
    int add(User user);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改
     *
     * @param user 
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
