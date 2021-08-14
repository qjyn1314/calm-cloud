package com.calm.gen;

import com.alibaba.fastjson.JSON;
import com.calm.gen.config.DbMessageInfo;
import com.calm.gen.config.GenConfig;
import com.calm.gen.service.GenService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * explain: 生成代码主类
 * </p>
 *
 * @author wangjunming
 * @since 2021/7/17 14:33
 */
@Slf4j
public final class GenExecute {

    public static void main(String[] args) {
        DbMessageInfo dbMessageInfo = new DbMessageInfo();
        //--todo 此处配置数据库连接信息
//        dbMessageInfo.setUrl();
//        dbMessageInfo.setUsername();
//        dbMessageInfo.setPassword();
        GenConfig genConfig = new GenConfig();
        genConfig.setDbMessageInfo(dbMessageInfo);
        //需要生成代码的表名
        String tableName = "gen_datasource_conf";

        log.info("生成代码的表名是....{}", tableName);

        genConfig.setTableName(tableName);
        //表备注
        genConfig.setComments("测试生成代码类");
        //表名前缀
        genConfig.setTablePrefix("");
        //包名-在模板中的import 使用此出配置的包名路径
//        genConfig.setEntity("com.calm.user.api.entity");
//        genConfig.setController("com.calm.user.persistence.controller");
//        genConfig.setService("com.calm.user.persistence.service");
//        genConfig.setServiceImpl("com.calm.user.persistence.service.impl");
//        genConfig.setMapper("com.calm.user.persistence.mapper");
//        genConfig.setXml("mappers");
        //生成的路径
        genConfig.setGenPath("D:\\gen_code");
        //使用特定的目录下的模板
//        genConfig.setSpecialTemplate("zcb");
        log.info("生成代码的基本信息是....{}", JSON.toJSON(genConfig));
        GenService genService = new GenService();
        genService.genCode(genConfig);
        log.info("Success....");
    }


}
