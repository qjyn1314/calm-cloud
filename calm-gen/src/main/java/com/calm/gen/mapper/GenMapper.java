package com.calm.gen.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/7/17 15:43
 */
public interface GenMapper {

    /**
     * 查询表信息
     *
     * @param tableName 表名称
     */
    @Select("select table_name AS tableName, engine, table_comment AS comments, NOW() datetime from information_schema.tables " +
            " where table_schema = (select database()) and table_name = #{tableName}" )
    Map<String, Object> queryTable(@Param("tableName" ) String tableName);

    /**
     * 查询表列信息
     *
     * @param tableName 表名称
     */
    @Select("select column_name columnName, data_type dataType, column_comment comments, column_key columnKey, extra ," +
            "is_nullable as isNullable,column_type as columnType from information_schema.columns " +
            " where table_name = #{tableName} and table_schema = (select database()) order by ordinal_position" )
    List<Map<String, Object>> queryColumns(@Param("tableName" ) String tableName);


}
