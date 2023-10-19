package com.gn.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author gn
 * @since 2023-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 上级资源id
     */
    private String parentId;

    /**
     * url
     */
    private String url;

    /**
     * 资源编码
     */
    private String resources;

    /**
     * path
     */
    private String path;

    /**
     * 前端路由名称
     */
    private String name;

    /**
     * 前端路由
     */
    private String component;

    /**
     * 资源名称
     */
    private String title;

    /**
     * 资源级别
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 类型menu、button
     */
    private String type;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改人
     */
    private String updetedBy;


}