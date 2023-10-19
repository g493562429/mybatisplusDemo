package com.gn.demo.service;

import com.gn.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author gn
 * @since 2023-08-02
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查询分页数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<Menu>
     */
    IPage<Menu> findListByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加
     *
     * @param menu 
     * @return int
     */
    int add(Menu menu);

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
     * @param menu 
     * @return int
     */
    int updateData(Menu menu);

    /**
     * id查询数据
     *
     * @param id id
     * @return Menu
     */
    Menu findById(Long id);
}
