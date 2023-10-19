package com.gn.demo.service;

import com.gn.demo.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author gn
 * @since 2023-08-02
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 查询分页数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<RoleMenu>
     */
    IPage<RoleMenu> findListByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加
     *
     * @param roleMenu 
     * @return int
     */
    int add(RoleMenu roleMenu);

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
     * @param roleMenu 
     * @return int
     */
    int updateData(RoleMenu roleMenu);

    /**
     * id查询数据
     *
     * @param id id
     * @return RoleMenu
     */
    RoleMenu findById(Long id);
}
