package com.gn.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gn.demo.entity.DemoData;
import com.gn.demo.service.IDemoDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * excel测试实体类 前端控制器
 *
 * @author gn
 * @since 2023-04-27
 */
@Api(tags = {"excel测试实体类"})
@RestController
@RequestMapping("/demo-data")
@Slf4j
public class DemoDataController {

    @Resource
    private IDemoDataService demoDataService;

    @ApiOperation(value = "新增excel测试实体类")
    @PostMapping("/add")
    public int add(@RequestBody DemoData demoData){
        return demoDataService.add(demoData);
    }

    @ApiOperation(value = "删除excel测试实体类")
    @PostMapping("/delete")
    public int delete(Long id){
        return demoDataService.delete(id);
    }

    @ApiOperation(value = "更新excel测试实体类")
    @PostMapping("/update")
    public int update(@RequestBody DemoData demoData){
        return demoDataService.updateData(demoData);
    }

    @ApiOperation(value = "查询excel测试实体类分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<DemoData> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return demoDataService.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询excel测试实体类")
    @GetMapping("getById")
    public DemoData getById(Long id){
        return demoDataService.findById(id);
    }

}
