package com.gn.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @author gn
 * @since 2023-08-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(加密)
     */
    private String password;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private String flag;

    /**
     * 盐值
     */
    private String salt;

    /**
     * token
     */
    private String token;

    /**
     * QQ 第三方登录Oppen_ID唯一表示
     */
    private String qqOppenId;

    /**
     * 密码（明文）
     */
    private String pwd;

    /**
     * 最后一次更新密码时间
     */
    private Date updatePasswordTime;


}