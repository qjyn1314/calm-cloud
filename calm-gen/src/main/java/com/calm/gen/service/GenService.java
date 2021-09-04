package com.calm.gen.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.calm.common.exception.CalmException;
import com.calm.gen.config.DbMessageInfo;
import com.calm.gen.config.GenConfig;
import com.calm.gen.config.SqlSessionService;
import com.calm.gen.mapper.GenMapper;
import com.calm.gen.util.CodeGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * explain: 生成代码服务
 * </p>
 *
 * @author wangjunming
 * @since 2021/7/17 15:43
 */
@Slf4j
public class GenService {

    public void genCode(GenConfig genConfig) {
        String tableName = genConfig.getTableName();
        Assert.isFalse(CharSequenceUtil.isBlank(tableName),"请使用正确的表名。");
        DbMessageInfo dbMessageInfo = genConfig.getDbMessageInfo();
        SqlSession sqlSession = SqlSessionService.me().handleSession(dbMessageInfo, GenMapper.class);
        GenMapper genMapper = sqlSession.getMapper(GenMapper.class);
        Map<String, Object> table = genMapper.queryTable(tableName);
        Assert.notNull(table,"未查找到表信息。");
        log.info("表信息是...{}", JSON.toJSON(table));
        List<Map<String, Object>> columns = genMapper.queryColumns(tableName);
        log.info("表的列信息是...{}", JSON.toJSON(columns));
        String genPath = genConfig.getGenPath();
        Assert.isFalse(CharSequenceUtil.isBlank(genPath),"请确定生成文件所输出的文件夹。");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        CodeGenUtils.generatorCode(genConfig, table, columns, zip);
        //创建文件夹
        File file = new File(genPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //压缩文件生成的路径
        genPath = genPath + File.separator + tableName + "_" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".zip";
        String fileGenPath = genPath.replaceAll("\\\\","/");
        log.info("生成的文件名...- file:///{}", fileGenPath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(genPath);
            byte[] data = outputStream.toByteArray();
            IoUtil.write(fileOutputStream, Boolean.TRUE, data);
        } catch (Exception e) {
            log.error("生成文件失败，请检查文件路径是否正确。", e);
        } finally {
            IoUtil.close(zip);
            IoUtil.close(fileOutputStream);
            IoUtil.close(outputStream);
        }
    }
}
