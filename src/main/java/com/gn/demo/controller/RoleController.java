package com.gn.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gn.demo.entity.Role;
import com.gn.demo.service.IRoleService;
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
 * @since 2023-04-20
 */
@Api(tags = {""})
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public int add(@RequestBody Role role){
        return roleService.add(role);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public int delete(Long id){
        return roleService.delete(id);
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public int update(@RequestBody Role role){
        return roleService.updateData(role);
    }

    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<Role> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return roleService.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询")
    @GetMapping("getById")
    public Role getById(Long id){
        return roleService.findById(id);
    }

}
