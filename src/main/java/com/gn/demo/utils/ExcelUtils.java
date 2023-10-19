//package com.gn.demo.utils;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.write.metadata.WriteSheet;
//import com.google.common.collect.Lists;
//import org.apache.poi.ss.util.SheetBuilder;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//
//public class ExcelUtils {
//
//    /**
//     * 将给定数据写入到 Excel 文件中并保存
//     *
//     * @param dataMapList 要写入的数据列表，每个元素代表一张 sheet 的数据，
//     *                    其中 Map 中的 key 为列名，value 为单元格值
//     * @param filePath    文件保存路径
//     * @throws IOException 如果文件操作失败则抛出 IOException 异常
//     */
//    public static void writeToFile(List<Map<String, Object>> dataMapList, String filePath) throws IOException {
//        // 创建 Workbook 对象
//        List<WriteSheet> writeSheetList = new ArrayList<>();
//        for (int i = 0; i < dataMapList.size(); i++) {
//            WriteSheet writeSheet = EasyExcel.writerSheet(i + 1, "Sheet" + (i + 1)).build();
//            writeSheetList.add(writeSheet);
//        }
//        ExcelWriter excelWriter = EasyExcel.write(filePath).build();
//
//        // 将数据写入 Excel 文件
//        for (int i = 0; i < dataMapList.size(); i++) {
//            List<List<Object>> dataList = new ArrayList<>();
//            Map<String, Object> dataMap = dataMapList.get(i);
//
//            // 生成表头
//            List<Object> headerList = new ArrayList<>();
//            for (String key : dataMap.keySet()) {
//                headerList.add(key);
//            }
//            dataList.add(headerList);
//
//            // 生成数据行
//            List<Object> valueList = new ArrayList<>();
//            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
//                valueList.add(entry.getValue());
//            }
//            dataList.add(valueList);
//
//            excelWriter.write(dataList, writeSheetList.get(i));
//        }
//
//        // 关闭 Workbook 对象
//        excelWriter.finish();
//    }
//
//    /**
//     * 将给定数据写入到 Excel 文件中并输出为 OutputStream 对象
//     *
//     * @param dataMapList  要写入的数据列表，每个元素代表一张 sheet 的数据，
//     *                     其中 Map 中的 key 为列名，value 为单元格值
//     * @param outputStream 已打开的 OutputStream 对象
//     * @throws IOException 如果文件操作失败则抛出 IOException 异常
//     */
//    public static void writeToStream(List<Map<String, Object>> dataMapList, OutputStream outputStream) throws IOException {
//        // 创建 Workbook 对象
//        List<WriteSheet> writeSheetList = Lists.newArrayList();
//        for (int i = 0; i < dataMapList.size(); i++) {
//            WriteSheet writeSheet = EasyExcel.writerSheet(i + 1, "Sheet" + (i + 1)).build();
//            writeSheetList.add(writeSheet);
//        }
//        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
//
//        // 将数据写入 Excel 文件
//        for (int i = 0; i < dataMapList.size(); i++) {
//            List<List<Object>> dataList = new ArrayList<>();
//            Map<String, Object> dataMap = dataMapList.get(i);
//
//            // 生成表头
//            List<Object> headerList = new ArrayList<>();
//            for (String key : dataMap.keySet()) {
//                headerList.add(key);
//            }
//            dataList.add(headerList);
//
//            // 生成数据行
//            List<Object> valueList = new ArrayList<>();
//            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
//                valueList.add(entry.getValue());
//            }
//            dataList.add(valueList);
//
//            excelWriter.write(dataList, writeSheetList.get(i));
//        }
//
//        // 关闭 Workbook 对象
//        excelWriter.finish();
//    }
//
//    /**
//     * 将给定数据写入到 Excel 文件中并返回文件的 byte[] 数组
//     *
//     * @param dataMapList 要写入的数据列表，每个元素代表一张 sheet 的数据，
//     *                    其中 Map 中的 key 为列名，value 为单元格值
//     * @return 带有写入数据的 Excel 文件的byte[] 数组表示
//     **/
//    public static byte[] writeBytes(List<Map<String, Object>> dataMapList) throws IOException {
//        // 创建临时文件
//        String tempDirPath = System.getProperty("java.io.tmpdir");
//        Path filePath = Paths.get(tempDirPath + File.separator + UUID.randomUUID().toString() + ".xlsx");
//
//        // 写入 Excel 文件到临时文件并读取其内容为 byte[] 数组
//        try {
//            writeToFile(dataMapList, filePath.toString());
//            return Files.readAllBytes(filePath);
//        } finally {
//            // 删除临时文件
//            Files.delete(filePath);
//        }
//    }
//
//
//    public static void main(String[] args) {
//        List<String> blobList = Lists.newArrayList();
//        blobList.add("性别");
////        List<DemoData> data = new ArrayList<>();
////        data.add(new DemoData("Tom", 18, "男"));
////        data.add(new DemoData("Jerry", 20, "女"));
//        // 模板文件路径
//        String templatePath = "C:\\Users\\sarlin\\Desktop\\template.xlsx";
//
//        // 输出文件路径
//        String outputPath = "C:\\Users\\sarlin\\Desktop\\output.xlsx";
//        // 创建Sheet对象，并指定sheet名称
//        Sheet sheet = new Sheet(1, 0);
//
//
//        DataValidationBuilder validation = sheet.getDataValidationHelper().createValidation();
//
//
//        // 第一个参数表示sheet页码，第二个参数表示表头行数，如果没有表头则传0
//        //创建Sheet对象，指定sheet号为1，从第0行开始（headLineMun不再需要）
//        Sheet sheet = new SheetBuilder(new XSSFWorkbook(), )
//                //设置sheet名称（可选）
//                .sheetName("Sheet1")
//                //设置是否自适应宽度（可选，默认为true）
//                .autoWidth(true)
//                //设置是否写入表头（可选，默认为true）
//                .needHead(true)
//                //设置表头（可选），字符串数组类型
//                .head(new String[]{"字段1", "字段2", "字段3"})
//                .build();
//
//        // 设置sheet名称
//        sheet.setSheetName("Sheet1");
//
//        // 定义有多少行数据，注意其中的泛型类型与excel的数据格式对应
//        List<List<String>> data = new ArrayList<>();
//        data.add(Arrays.asList("姓名", "性别"));
//        data.add(Arrays.asList("张三", "男"));
//        data.add(Arrays.asList("李四", "女"));
//        data.add(Arrays.asList("王五", "男"));
//
//        // 定义有哪些列需要下拉框
//        Map<Integer, String[]> dropdownMap = new HashMap<>();
//        dropdownMap.put(1, new String[]{"男", "女"});
//
//        // 设置下拉框
//        sheet.setDropDownValues(dropdownMap);
//
//        // 写入数据
//        EasyExcel.write(fileName).sheet(sheet).doWrite(data);
//
//
//    }
//}