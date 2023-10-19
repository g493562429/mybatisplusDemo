//package com.gn.demo.dto;
//
//import com.alibaba.excel.annotation.ExcelIgnore;
//import com.alibaba.excel.annotation.ExcelProperty;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class DemoDataDto {
//
//    @ExcelProperty("姓名")
//    private String name;
//
//    @ExcelProperty("年龄")
//    private Integer age;
//
//    @ExcelProperty("性别")
//    private String sex;
//
//    @TableId(value = "id", type = IdType.AUTO)
//    @ExcelIgnore
//    private Long id;
//}