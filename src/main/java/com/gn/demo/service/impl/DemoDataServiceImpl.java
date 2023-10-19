package com.gn.demo.service.impl;

import com.gn.demo.entity.DemoData;
import com.gn.demo.handler.AbstractHandle;
import com.gn.demo.mapper.DemoDataMapper;
import com.gn.demo.service.IDemoDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * excel测试实体类 服务实现类
 *
 * @author gn
 * @since 2023-04-27
 */
@Service
@Slf4j
public class DemoDataServiceImpl extends ServiceImpl<DemoDataMapper, DemoData> implements IDemoDataService, AbstractHandle<DemoData> {

    @Override
    public IPage<DemoData> findListByPage(Integer pageNum, Integer pageSize){
        IPage<DemoData> wherePage = new Page<>(pageNum, pageSize);
        DemoData where = new DemoData();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(DemoData demoData){
        return baseMapper.insert(demoData);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(DemoData demoData){
        return baseMapper.updateById(demoData);
    }

    @Override
    public DemoData findById(Long id){
        return  baseMapper.selectById(id);
    }

    @Override
    public Object getService(List<DemoData> dataList) {



        return null;
    }

    @Override
    public Object doSomething(List<DemoData> dataList) {
        log.info("DemoDataServiceImpl.doSomething.dataList.size=[{}]", dataList.size());
        List<String> names = dataList.stream().map(DemoData::getName).collect(Collectors.toList());
        List<DemoData> list = lambdaQuery().in(DemoData::getName, names).list();
        if (!CollectionUtils.isEmpty(list)) {
            List<String> exits = list.stream().map(DemoData::getName).collect(Collectors.toList());
            String msg = String.join(",", exits) + "已存在!!!";
            return msg;
        }
        saveBatch(dataList);
        return "success";
    }
}
