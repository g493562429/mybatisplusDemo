package com.gn.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gn.demo.entity.Menu;
import com.gn.demo.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  前端控制器
 *
 * @author gn
 * @since 2023-08-02
 */
@Api(tags = {""})
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public int add(@RequestBody Menu menu){
        return menuService.add(menu);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public int delete(Long id){
        return menuService.delete(id);
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public int update(@RequestBody Menu menu){
        return menuService.updateData(menu);
    }

    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<Menu> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return menuService.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询")
    @GetMapping("getById")
    public Menu getById(Long id){
        return menuService.findById(id);
    }

}
