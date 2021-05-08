package com.gn.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gn.demo.entity.User;
import com.gn.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表 前端控制器
 *
 * @author gn
 * @since 2021-05-08
 */
@Api(tags = {"用户表"})
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "新增用户表")
    @PostMapping("/add")
    public int add(@RequestBody User user) {
        return userService.add(user);
    }

    @ApiOperation(value = "删除用户表")
    @PostMapping("/delete")
    public int delete(Long id) {
        return userService.delete(id);
    }

    @ApiOperation(value = "更新用户表")
    @PostMapping("/update")
    public int update(@RequestBody User user) {
        return userService.updateData(user);
    }

    @ApiOperation(value = "查询用户表分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<User> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return userService.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询用户表")
    @GetMapping("getById")
    public User getById(Long id) {
        return userService.findById(id);
    }

}
