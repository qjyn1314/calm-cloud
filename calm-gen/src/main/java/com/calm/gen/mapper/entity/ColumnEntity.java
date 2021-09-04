package com.calm.gen.mapper.entity;

import lombok.Data;

/**
 * 列属性
 */
@Data
public class ColumnEntity {

    /**
     * 列表
     */
    private String columnName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 备注
     */
    private String comments;

    /**
     * 驼峰属性
     */
    private String caseAttrName;

    /**
     * 普通属性
     */
    private String lowerAttrName;

    /**
     * 大写的属性名
     */
    private String capitalAttrName;

    /**
     * 属性类型
     */
    private String attrType;

    /**
     * 其他信息
     */
    private String extra;

    /**
     * 字段类型
     */
    private String columnType;

}
