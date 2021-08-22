package com.calm.gen.excel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExcelModelByEasyExcel {

    private String name;
    private String sex;
    private String age;
    private String hobby;

}
