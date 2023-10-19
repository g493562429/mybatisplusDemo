package com.gn.demo.service;

import com.gn.demo.entity.DemoData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * excel测试实体类 服务类
 *
 * @author gn
 * @since 2023-04-27
 */
public interface IDemoDataService extends IService<DemoData> {

    /**
     * 查询excel测试实体类分页数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return IPage<DemoData>
     */
    IPage<DemoData> findListByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加excel测试实体类
     *
     * @param demoData excel测试实体类
     * @return int
     */
    int add(DemoData demoData);

    /**
     * 删除excel测试实体类
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改excel测试实体类
     *
     * @param demoData excel测试实体类
     * @return int
     */
    int updateData(DemoData demoData);

    /**
     * id查询数据
     *
     * @param id id
     * @return DemoData
     */
    DemoData findById(Long id);
}
