//package com.gn.demo.utils;/*
// * @Description:
// * @Author: GuiNing
// * @Date: 2023/4/24 16:09
// */
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.write.metadata.WriteSheet;
//import com.gn.demo.dto.DemoData;
//import com.gn.demo.listener.ExcelListener;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ExcelUtil {
//
//    public List<DemoData> getDataFromExcel(String fileName) {
//        List<DemoData> data = new ArrayList<>();
//        try (InputStream is = new FileInputStream(fileName)) {
//            // Excel读取操作
//            ExcelListener listener = new ExcelListener();
//            ExcelReader reader = new ExcelReader(is, null, listener);
//            reader.read();
//            data = listener.getDataList();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//    public void writeDataToExcel(List<DemoData> data, String fileName) {
//        try (OutputStream os = new FileOutputStream(fileName)) {
//            // Excel写入操作
//            ExcelWriter writer = EasyExcel.write(os, DemoData.class).build();
//            WriteSheet sheet = EasyExcel.writerSheet("sheet1").build();
//            writer.write(data, sheet);
//            writer.finish();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
