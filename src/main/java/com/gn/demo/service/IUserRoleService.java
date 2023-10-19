package com.gn.demo.service;

import com.gn.demo.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author gn
 * @since 2023-08-02
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 查询分页数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<UserRole>
     */
    IPage<UserRole> findListByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加
     *
     * @param userRole 
     * @return int
     */
    int add(UserRole userRole);

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
     * @param userRole 
     * @return int
     */
    int updateData(UserRole userRole);

    /**
     * id查询数据
     *
     * @param id id
     * @return UserRole
     */
    UserRole findById(Long id);
}
