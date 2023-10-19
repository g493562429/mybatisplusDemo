package com.gn.demo.handler;/*
 * @Description: 处理下拉
 * @Author: GuiNing
 * @Date: 2023/4/25 15:56
 */

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;
import java.util.Map;
/**
 * excel表格式处理类
 *
 * @author gn
 * @since 2023-04-20
 */

@Data
@Slf4j
public class SelectSheetWriteHandle implements SheetWriteHandler {

    private static final int TITLE_ROW_INDEX = 0;

    /**
     * 下拉框数据项map, key为第几列,List<String>为下拉框数据项
     */
    private Map<Integer, List<String>> selectMap;

    /**
     * 设置下拉框位置首行
     */
    private Integer firstRow;

    /**
     * 设置下拉框位置末行
     */
    private Integer lastRow;

    /**
     * 数据字典集
     */
    private char[] alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public SelectSheetWriteHandle(Map<Integer, List<String>> selectMap, Integer firstRow, Integer lastRow) {
        this.selectMap = selectMap;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder workbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (selectMap == null || selectMap.size() == 0) {
            return;
        }
        Sheet sheet = writeSheetHolder.getSheet();
        //设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();

        //sheet名
        String dictSheetName = "字典sheet";
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        //数据字典的sheet页(下拉框的值)
        Sheet dictSheet = workbook.createSheet(dictSheetName);
        //将数据字典的sheet隐藏(对用户不可见)
        workbook.setSheetHidden(workbook.getSheetIndex(dictSheet), true);
        for (Map.Entry<Integer, List<String>> entry : selectMap.entrySet()) {
            // 起始行、终止行、起始列、终止列
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, entry.getKey(), entry.getKey());
            int rowLen = entry.getValue().size();
            log.info("holder.rowLen=[{}]", rowLen);
            // 设置字典sheet页的值 每一列一个字典项
            for (int i = 0; i < rowLen; i++) {
                Row row = dictSheet.getRow(i);
                if (row == null) {
                    row = dictSheet.createRow(i);
                }
                row.createCell(entry.getKey()).setCellValue(entry.getValue().get(i));
            }
            //下拉框所在的列
            String excelColumn = getExcelColumn(entry.getKey());
            // 下拉框数据来源 eg:字典sheet!$B1:$B2
            String refers = dictSheetName + "!$" + excelColumn + "$1:$" + excelColumn + "$" + rowLen;
            // 创建可被其他单元格引用的名称
            Name name = workbook.createName();
            // 设置名称的名字
            name.setNameName("dict" + entry.getKey());
            // 设置公式
            name.setRefersToFormula(refers);

            //设置下拉框数据
            DataValidationConstraint constraint = helper.createFormulaListConstraint("dict" + entry.getKey());
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            //处理Excel兼容性问题
            if (dataValidation instanceof HSSFDataValidation) {
                dataValidation.setSuppressDropDownArrow(false);
            } else {
                dataValidation.setSuppressDropDownArrow(true);
                dataValidation.setShowErrorBox(true);
            }

            //阻止输入非下拉框的值
            dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            dataValidation.createErrorBox("提示", "此值与单元格定义格式不一致!");

            sheet.addValidationData(dataValidation);
        }
    }

    /**
     * 将数字列转化成为字母列
     * 主要作用在于根据传入的列获取数据字典sheet中对应列
     *
     * @param num num
     * @return String
     */
    private String getExcelColumn(int num) {
        String column = "";
        int len = alphabet.length - 1;
        int first = num / len;
        int second = num % len;
        if (num <= len) {
            column = alphabet[num] + "";
        } else {
            column = alphabet[first - 1] + "";
            if (second == 0) {
                column = column + alphabet[len] + "";
            } else {
                column = column + alphabet[second - 1] + "";
            }
        }
        return column;
    }

}
