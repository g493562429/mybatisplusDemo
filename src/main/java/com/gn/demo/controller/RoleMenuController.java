package com.gn.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gn.demo.entity.RoleMenu;
import com.gn.demo.service.IRoleMenuService;
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
@RequestMapping("/role-menu")
@Slf4j
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public int add(@RequestBody RoleMenu roleMenu){
        return roleMenuService.add(roleMenu);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public int delete(Long id){
        return roleMenuService.delete(id);
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public int update(@RequestBody RoleMenu roleMenu){
        return roleMenuService.updateData(roleMenu);
    }

    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<RoleMenu> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return roleMenuService.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询")
    @GetMapping("getById")
    public RoleMenu getById(Long id){
        return roleMenuService.findById(id);
    }

}
