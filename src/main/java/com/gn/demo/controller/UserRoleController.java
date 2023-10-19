package com.gn.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gn.demo.entity.UserRole;
import com.gn.demo.service.IUserRoleService;
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
@RequestMapping("/user-role")
@Slf4j
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public int add(@RequestBody UserRole userRole){
        return userRoleService.add(userRole);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public int delete(Long id){
        return userRoleService.delete(id);
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public int update(@RequestBody UserRole userRole){
        return userRoleService.updateData(userRole);
    }

    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<UserRole> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return userRoleService.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询")
    @GetMapping("getById")
    public UserRole getById(Long id){
        return userRoleService.findById(id);
    }

}
