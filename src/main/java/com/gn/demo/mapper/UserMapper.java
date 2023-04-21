package com.gn.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gn.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 Mapper 接口
 *
 * @author gn
 * @since 2021-05-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
