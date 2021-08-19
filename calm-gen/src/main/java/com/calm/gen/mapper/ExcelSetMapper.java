package com.calm.gen.mapper;

import com.calm.gen.excel.ExcelSet;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExcelSetMapper {

    @Select("select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables " +
            " where table_schema = (select database()) and table_name = #{tableName}")
    List<ExcelSet> selectExcelSetByType(Integer budgetObject);

}
