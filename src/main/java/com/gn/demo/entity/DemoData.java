package com.gn.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * excel测试实体类
 *
 * @author gn
 * @since 2023-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("demo_data")
public class DemoData implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 2)
    private String sex;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

}
