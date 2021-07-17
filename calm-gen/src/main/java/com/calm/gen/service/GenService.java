package com.calm.gen.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.calm.common.exception.CalmException;
import com.calm.gen.config.DbMessageInfo;
import com.calm.gen.config.GenConfig;
import com.calm.gen.config.MybatisGenConfig;
import com.calm.gen.mapper.GenMapper;
import com.calm.gen.util.CodeGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        DbMessageInfo dbMessageInfo = genConfig.getDbMessageInfo();
        String tableName = genConfig.getTableName();
        if(StrUtil.isBlank(tableName)){
            throw new CalmException("请使用正确的表名。");
        }
        MybatisGenConfig mybatisGenConfig = new MybatisGenConfig(dbMessageInfo);
        SqlSessionFactory sqlSessionFactory = mybatisGenConfig.initMybatisSqlSessionFactory(GenMapper.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GenMapper genMapper = sqlSession.getMapper(GenMapper.class);
        Map<String, String> table = genMapper.queryTable(tableName);
        log.info("表信息是...{}", JSON.toJSON(table));
        List<Map<String, String>> columns = genMapper.queryColumns(tableName);
        log.info("表的列信息是...{}", JSON.toJSON(columns));
        String genPath = genConfig.getGenPath();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        CodeGenUtils.generatorCode(genConfig, table, columns, zip);
        //压缩文件生成的路径
        genPath = genPath + File.separator + tableName + "_" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".zip";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(genPath);
            byte[] data = outputStream.toByteArray();
            IoUtil.write(fileOutputStream, Boolean.TRUE, data);
        } catch (IOException e) {
            log.error("生成文件,", e);
        } finally {
            IoUtil.close(zip);
            IoUtil.close(fileOutputStream);
            IoUtil.close(outputStream);
        }
    }
}
