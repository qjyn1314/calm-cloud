package com.calm.gen.excel;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.ListUtil;
import com.calm.common.utils.ResponseUtils;
import com.calm.gen.config.DbMessageInfo;
import com.calm.gen.config.SqlSessionService;
import com.calm.gen.mapper.ExcelSetMapper;
import io.undertow.servlet.spec.HttpServletResponseImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * EasyPoi-官网：
 * http://doc.wupaas.com/docs/easypoi/easypoi-1c0u8jachdq52
 * 相关博客：
 * https://blog.csdn.net/dengfengan/article/details/97940840
 * 表格的背景色：
 * https://blog.csdn.net/m0_37626813/article/details/78305920
 * 设置单元格批注：
 * https://www.cnblogs.com/zhjh256/p/10927707.html
 * @author wangjunming@zhichubao.com 2021/8/19 14:58
 */
@Slf4j
public class ExcelStartTest {


    public static void main(String[] args) throws Exception {
//        DbMessageInfo dbMessageInfo = new DbMessageInfo();
//        dbMessageInfo.setUrl("jdbc:mysql://127.0.0.1:3306/study?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC");
//        SqlSession sqlSession = SqlSessionService.me().handleSession(dbMessageInfo, ExcelSetMapper.class);
//        ExcelSetMapper mapper = sqlSession.getMapper(ExcelSetMapper.class);

        //此表格的配置信息
        List<ExcelSet> settings = initExcelSetting();
        Workbook book = ExcelStartUtil.exportBySettingAndEmptyData("测试导出带批注",settings);
        FileOutputStream fos = new FileOutputStream("D:\\workspace\\project_excel\\test_excel.xlsx");
        assert book != null;
        book.write(fos);
        fos.close();
        log.info("success......");

        ExportParams exportParams = new ExportParams();
        List<ExcelModel> excelModels = initData();
        Workbook sheets = ExcelStartUtil.exportBySettingAndEmptyData(exportParams, settings, excelModels);
        FileOutputStream expoFos = new FileOutputStream("D:\\workspace\\project_excel\\test_export_excel.xlsx");
        sheets.write(expoFos);
        expoFos.close();

        log.info("success......");
    }

    private static List<ExcelModel> initData() {
        List<ExcelModel> data = ListUtil.list(false);
        ExcelModel model01 = new ExcelModel().setCode("预算编码-编码-01").setCreditCode("社会信用代码-01").setProductCode("产品线-01").setType("产品线编码-01");
        data.add(model01);
        ExcelModel model02 = new ExcelModel().setCode("预算编码-编码-02").setCreditCode("社会信用代码-02").setProductCode("产品线-02").setType("产品线编码-02");
        data.add(model02);
        ExcelModel model03 = new ExcelModel().setCode("预算编码-编码-03").setCreditCode("社会信用代码-03").setProductCode("产品线-03").setType("产品线编码-03");
        data.add(model03);
        ExcelModel model04 = new ExcelModel().setCode("预算编码-编码-04").setCreditCode("社会信用代码-04").setProductCode("产品线-04").setType("产品线编码-04");
        data.add(model04);
        ExcelModel model05 = new ExcelModel().setCode("预算编码-编码-05").setCreditCode("社会信用代码-05").setProductCode("产品线-05").setType("产品线编码-05");
        data.add(model05);
        ExcelModel model06 = new ExcelModel().setCode("预算编码-编码-06").setCreditCode("社会信用代码-06").setProductCode("产品线-06").setType("产品线编码-06");
        data.add(model06);
        return data;
    }

    private static List<ExcelSet> initExcelSetting() {
        List<ExcelSet> sets = ListUtil.list(false);
        ExcelSet set01 = new ExcelSet().setTitle("预算编码").setAnnotation("必填，不超过20个字符").setRequired(true);
        sets.add(set01);
        ExcelSet set02 = new ExcelSet().setTitle("法律实体社会信用代码").setAnnotation("必填").setRequired(true);
        sets.add(set02);
        ExcelSet set03 = new ExcelSet().setTitle("成本中心/产品线类型").setAnnotation("必填：成本中心 或 产品线").setRequired(true);
        sets.add(set03);
        ExcelSet set04 = new ExcelSet().setTitle("成本中心/产品线编码").setAnnotation("必填，填写对应的成本中心编码/产品线编码").setRequired(true);
        sets.add(set04);
        return sets;
    }


}
