package com.calm.gen.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * explain: excel工具类
 * </p>
 *
 * @author wangjunming@zhichubao.com  2021/8/6 22:04
 */
@Slf4j
public final class ExcelStartUtil {

    /**
     * 配置导出excel
     *
     * @author wangjunming@zhichubao.com 2021/8/7 20:30
     */
    public static Workbook exportBySettingAndEmptyData(ExportParams exportParams,List<ExcelSet> settings, Collection<?> dataList) {
        List<ExcelExportEntity> columnList = settings.stream().map( set -> new ExcelExportEntity(set.getTitle())).collect(Collectors.toList());
        return ExcelExportUtil.exportExcel(exportParams, columnList, dataList);
    }

    /**
     * 配置导出excel
     *
     * @author wangjunming@zhichubao.com 2021/8/7 20:30
     */
    public static Workbook exportBySettingAndEmptyData(String sheetName,List<ExcelSet> settings) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        /******设置单元格是否显示网格线******/
//        sheet.setDisplayGridlines(false);
        XSSFRow row = sheet.createRow(0);
        /******设置头单元格样式******/
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font fontHeader = wb.createFont();
//        fontHeader.setBold(true);
        fontHeader.setFontHeight((short) 20);
        fontHeader.setColor(IndexedColors.RED.getIndex());
        style.setFont(fontHeader);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        /******设置头内容******/
        for (int i = 0; i < settings.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue("  " +settings.get(i).getTitle() + "  ");
            cell.setCellStyle(style);
        }
        return wb;
    }

}
