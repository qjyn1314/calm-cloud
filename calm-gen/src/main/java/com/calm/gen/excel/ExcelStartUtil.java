package com.calm.gen.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    /***
     * 下载文件类型
     */
    public static class FileType {
        public static Integer BUDGET_OBJECT = 1;
        public static Integer FINANCIAL_ACCOUNT = 2;
        public static Integer BATCH_FINANCIAL_ADJUSTMENT = 3;
        public static Integer COST_PRODUCT_YEAR = 4;
        public static Integer COST_PRODUCT_QUARTERLY = 5;
        public static Integer COST_PRODUCT_MONTH = 6;
        public static Integer PROJECT_BUDGET = 7;
        public static Integer SCHOOL_YEAR_BUDGET = 8;
        public static Integer ACCOUNT_BUDGET = 9;
    }

    /**
     * 配置导出excel
     * @author wangjunming@zhichubao.com 2021/8/7 20:30
     */
    public static Workbook exportBySettingAndEmptyData(ExportParams exportParams, List<ExcelSet> settings, Collection<?> dataList) {
        List<ExcelExportEntity> columnList = settings.stream().map(set -> new ExcelExportEntity(set.getTitle())).collect(Collectors.toList());
        return ExcelExportUtil.exportExcel(exportParams, columnList, dataList);
    }

    /**
     * 据配置 导出excel 模板
     * @author wangjunming@zhichubao.com 2021/8/7 20:30
     */
    public static Workbook exportBySettingAndEmptyData(String sheetName, List<ExcelSet> settings) {
        if (StrUtil.isBlank(sheetName) || CollUtil.isEmpty(settings)) {
            return null;
        }
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        /*设置单元格是否显示网格线*/
        //创建第一行内容
        XSSFRow row = sheet.createRow(0);
        /*设置头单元格样式*/
        XSSFCellStyle style = wb.createCellStyle();
        Font fontHeader = wb.createFont();
        fontHeader.setFontName("宋体");
        fontHeader.setFontHeightInPoints((short) 14);
        fontHeader.setColor(IndexedColors.RED.getIndex());
        style.setFont(fontHeader);
        //style.setFillForegroundColor((short) 13);// 设置背景色
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setWrapText(true);//设置自动换行
        //存储最大列宽
        Map<Integer, Integer> maxWidth = new HashMap<>(settings.size());
        //创建绘图对象
        XSSFDrawing p = sheet.createDrawingPatriarch();
        /*设置每一列的标题内容*/
        for (int i = 0; i < settings.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(settings.get(i).getTitle());
            cell.setCellStyle(style);
            maxWidth.put(i, cell.getStringCellValue().getBytes().length * 256 + 200);
            // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
            XSSFComment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
            // 输入批注信息
            comment.setString(new XSSFRichTextString(settings.get(i).getAnnotation()));
            // 添加作者,选中B5单元格,看状态栏
            comment.setAuthor("管理员");
            cell.setCellComment(comment);
        }
        //设置列宽
        for (int i = 0; i < settings.size(); i++) {
            sheet.setColumnWidth(i, maxWidth.get(i));
        }
        return wb;
    }


    public static byte[] writeOuts(Workbook workbook) {
        if(workbook == null){
            return new byte[]{};
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("写入流异常", e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("关闭workbook异常", e);
            }
        }
        return outputStream.toByteArray();
    }

}
