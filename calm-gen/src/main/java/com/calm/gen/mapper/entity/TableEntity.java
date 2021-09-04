package com.calm.gen.mapper.entity;

import lombok.Data;

import java.util.List;

/**
 * 表属性
 */
@Data
public class TableEntity {

    /**
     * 名称
     */
    private String tableName;

    /**
     * 备注
     */
    private String comments;

    /**
     * 主键
     */
    private ColumnEntity pk;

    /**
     * 列名
     */
    private List<ColumnEntity> columns;

    /**
     * 驼峰类型
     */
    private String className;

    /**
     * 普通类型
     */
    private String lowerClassname;

    /**
     * controller的请求路径
     */
    private String pathName;

}
