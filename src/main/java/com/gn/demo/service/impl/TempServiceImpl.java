package com.gn.demo.service.impl;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/4/25 15:17
 */

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.gn.demo.entity.DemoData;
import com.gn.demo.handler.SelectSheetWriteHandle;
import com.gn.demo.listener.ExcelListener;
import com.gn.demo.service.TempService;
import com.gn.demo.utils.DateTimeUtils;
import com.gn.demo.utils.FileUtil;
import com.gn.demo.utils.FileUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class TempServiceImpl implements TempService {

    @Override
    public void getTemp(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        //动态模板需填充的数据
        List data = Lists.newArrayList();
//        data.add(new DemoDataDto("张三", 18, "男"));
//        data.add(new DemoDataDto("李四", 19, "女"));
//        data.add(new DemoDataDto("王五", 20, "男"));
        //下拉列表1数据项
        List<String> selectList1 = Lists.newArrayList();
        selectList1.add("男");
        selectList1.add("女");
        selectList1.add("中性");

        //将下拉列表数据放置在一个map中, key为下拉框所在列数,从0开始。如0——>A; 1——>B
        Map<Integer, List<String>> selectMap = Maps.newHashMap();
        selectMap.put(2, selectList1);

        //此处firstRow为需要设置下拉框的起始行位置,可根据自身需求需要调整设置
        Integer firstRow = 1;
        //此处lastRow为需要设置下拉框的截止行位置,可根据自身需求需要调整设置
        Integer lastRow = 1000;

        //使用模板填充导出的相关配置
        OutputStream out = null;
        BufferedOutputStream bos = null;

        try {
            String templateFileName = FileUtils.getPath() + File.separator + "template.xlsx";
            log.info("templateFileName=[{}]", templateFileName);
            //为动态生成的模板命名(带时间区分)
            String fileNameWithTime = "导入模板" + DateTimeUtils.formatDate(new Date(), DateTimeUtils.DATE_TIME_PATTERN2) + ".xlsx";

            //设置字符编码标准等
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(fileNameWithTime, "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

            out = response.getOutputStream();
            bos = new BufferedOutputStream(out);

            SelectSheetWriteHandle selectSheetWriteHandle = new SelectSheetWriteHandle(selectMap, firstRow, lastRow);
            ExcelWriter excelWriter = EasyExcel.write(bos)
                    .withTemplate(templateFileName)
                    .build();
            //此处用到的"registerWriteHandler()"用于设置相应格式,"SelectSheetWriteHandler(selectMap,firstRow,lastRow)"为封装的设置单元格下拉框的工具类
            WriteSheet writeSheet = EasyExcel.writerSheet()
                    .registerWriteHandler(selectSheetWriteHandle)
                    .build();

            //向下新增行填充config
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(data, fillConfig, writeSheet);
            excelWriter.finish();
            bos.flush();
            log.info("do end cast time:[{}]ms", System.currentTimeMillis() - start);
        } catch (IOException e) {
            e.printStackTrace();
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            try {
                response.getWriter().println("打印失败");
            } catch (Exception ex) {
                log.error("TempServiceImpl.getTemp println fail!!!");
                ex.printStackTrace();
            }
        } catch (Exception e) {
            log.error("TempServiceImpl.getTemp.error:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (!Objects.isNull(out)) {
                    out.close();
                }
                if (!Objects.isNull(bos)) {
                    bos.close();
                }
            } catch (IOException e) {
                log.warn("io stream close fail!!!");
            }

        }
    }

    @Override
    public void simpleRead(MultipartFile file) {
        log.info("file.name=[{}]", file.getOriginalFilename());
        File targetFile = null;
        try {
            //文件路径
            String path = FileUtils.getPath();
            targetFile = new File(path + File.separator + "temp.xlsx");
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //重点注意监听器读取的逻辑
        //fileName 文件
        //DemoData 类规则
        //DemoDataListener 监听器
        //sheet().doRead() 表的读取
        EasyExcel.read(targetFile, DemoData.class, new ExcelListener<DemoData>())
                .sheet()
                .doRead();
    }
}
