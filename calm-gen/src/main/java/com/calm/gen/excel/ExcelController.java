package com.calm.gen.excel;

import com.alibaba.excel.EasyExcel;
import com.calm.common.utils.ResponseUtils;
import com.calm.gen.mapper.ExcelSetMapper;
import com.calm.parent.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * <p>
 * explain:
 * 使用easyexcel 导入
 * 使用easypoi模板 导出
 * 下载模板：
 * 下载项目中的文件模板
 * 在使用easypoi的模板导出空数据，即下载模板
 * <p>
 * 官方文档：
 * https://www.yuque.com/easyexcel/doc/easyexcel
 * </p>
 *
 * @author wangjunming
 * @since 2021/6/4 11:23
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    private ExcelSetMapper excelSetMapper;

    /**
     * 下载模板可直接创建流并下载
     * @author wangjunming@zhichubao.com 2021/8/19 17:24
     */
    @ApiOperation("下载模板")
    @PostMapping("/template")
    public void excelTemplate(HttpServletResponse response) {
        List<ExcelSet> settings = excelSetMapper.selectExcelSetByType(ExcelStartUtil.FileType.BUDGET_OBJECT);
        String sheetName = "测试导出带批注";
        Workbook workbook = ExcelStartUtil.exportBySettingAndEmptyData(sheetName, settings);
        byte[] outs = ExcelStartUtil.writeOuts(workbook);
        String fileName = "测试导出带批注.xlsx";
        ResponseUtils.downloadXls(response,fileName,outs);
    }
    @ApiOperation("导入")
    @PostMapping("/import")
    public JsonResult<ExcelImportVO<ExcelModelByEasyExcel>> excelImport(@RequestParam("file") MultipartFile file) {
        ExcelImportVO<ExcelModelByEasyExcel> exportVO = new ExcelImportVO<>();
        try {
            EasyExcel.read(file.getInputStream(), ExcelModelByEasyExcel.class, new ExcelModelByEasyExcelListener(exportVO)).sheet().headRowNumber(0).doRead();
        } catch (Exception e) {
            log.error("读取excel失败。", e);
            return JsonResult.fail("读取excel失败");
        }
        return JsonResult.success(exportVO);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void excelExport() {









    }








}
