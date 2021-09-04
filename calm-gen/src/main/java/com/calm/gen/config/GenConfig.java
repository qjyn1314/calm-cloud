package com.calm.gen.config;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成配置
 */
@Data
public class GenConfig implements Serializable {

    /**
     * 作者
     */
    private String author = "Mr.Gen";

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;

    /**
     * 生成的路径-默认路径
     */
    private String genPath = "C:\\gen_code";

    /**
     * 数据库连接信息
     */
    private DbMessageInfo dbMessageInfo;

    /**
     * Entity包名
     */
    private String entity = "entity";
    /**
     * Controller包名
     */
    private String controller = "controller";
    /**
     * Service包名
     */
    private String service = "service";
    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    /**
     * Repository包名
     */
    private String repository = "repository";
    /**
     * Repository Impl包名
     */
    private String repositoryImpl = "repository.impl";
    /**
     * Mapper包名
     */
    private String mapper = "mapper";
    /**
     * Mapper XML包名
     */
    private String xml = "mappers";

    /**
     * template 文件夹下其他的模板使用
     */
    private String specialTemplate;

    /**
     * 创建时间
     */
    private String datetime = DateUtil.now();

}
