package com.gn.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author sarlin_gn
 * @Date 2021/5/8 15:41
 * @Desc 查询分页参数
 */
@Data
@ApiModel(description = "查询分页参数")
@AllArgsConstructor
public class PageDto {

    @ApiModelProperty(value = "页码，从1开始")
    private Integer pageNum;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;
}
