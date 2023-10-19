package com.gn.demo.service;

import com.gn.demo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author gn
 * @since 2023-04-20
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询分页数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<Role>
     */
    IPage<Role> findListByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加
     *
     * @param role 
     * @return int
     */
    int add(Role role);

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
     * @param role 
     * @return int
     */
    int updateData(Role role);

    /**
     * id查询数据
     *
     * @param id id
     * @return Role
     */
    Role findById(Long id);
}
