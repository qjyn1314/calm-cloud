package com.calm.gen.excel;

import com.calm.common.utils.ResponseUtils;
import com.calm.parent.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * explain:
 * 使用easyexcel 进行导入导出
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

    @ApiOperation("下载模板")
    @PostMapping("/template")
    public void excelTemplate(HttpServletResponse response) {






//        ResponseUtils.downloadXls();
    }
    @ApiOperation("导入")
    @PostMapping("/import")
    public JsonResult<ExcelExportVO> excelImport() {
        ExcelExportVO exportVO = new ExcelExportVO();
        return JsonResult.success(exportVO);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void excelExport() {
        ExcelExportVO<ExcelExportVO> exportVO = new ExcelExportVO();


    }








}
