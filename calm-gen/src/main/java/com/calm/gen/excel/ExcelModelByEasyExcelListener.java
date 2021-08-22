package com.calm.gen.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.experimental.Accessors;

public class ExcelModelByEasyExcelListener extends AnalysisEventListener<ExcelModelByEasyExcel> {

    private final ExcelImportVO<ExcelModelByEasyExcel> exportVO;

    public ExcelModelByEasyExcelListener(ExcelImportVO<ExcelModelByEasyExcel> exportVO) {
        this.exportVO = exportVO;
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(ExcelModelByEasyExcel excelModelByEasyExcel, AnalysisContext analysisContext) {


        exportVO.setSumCount(0);
    }

    /**
     * 所有数据解析完成了
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
