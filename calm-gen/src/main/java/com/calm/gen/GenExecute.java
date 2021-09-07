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
        // TODO 此处配置数据库连接信息
//        dbMessageInfo.setUrl();
//        dbMessageInfo.setUsername();
//        dbMessageInfo.setPassword();
        GenConfig genConfig = new GenConfig();
        genConfig.setDbMessageInfo(dbMessageInfo);
        //需要生成代码的表名
        String tableName = "zemd_org";
        log.info("生成代码的表名是....{}", tableName);
        genConfig.setTableName(tableName);
        //自定义表备注
//        genConfig.setComments("业务组织架构");
        //表名前缀
        genConfig.setTablePrefix("zemd_");
        //包名-在模板中的import 使用此出配置的包名路径
        genConfig.setEntity("com.zhichubao.emd.domain.entity");
        genConfig.setXml("mapper");
        genConfig.setMapper("com.zhichubao.emd.infra.mapper");
        genConfig.setRepository("com.zhichubao.emd.domain.repository");
        genConfig.setRepositoryImpl("com.zhichubao.emd.domain.repository.impl");
        genConfig.setService("com.zhichubao.emd.app.service");
        genConfig.setServiceImpl("com.zhichubao.emd.app.service.impl");
        genConfig.setController("com.zhichubao.emd.api.controller.v1");
        //生成的路径
        genConfig.setGenPath("D:\\gen_code");
        //使用特定的目录下的模板
        genConfig.setSpecialTemplate("zcb_saas");
        genConfig.setAuthor("wangjunming@zhichubao.com");
        log.info("生成代码的基本信息是....{}", JSON.toJSON(genConfig));
        GenService genService = new GenService();
        genService.genCode(genConfig);
        log.info("Success....");
    }


}
