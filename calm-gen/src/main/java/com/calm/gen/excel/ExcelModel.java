package com.calm.gen.excel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExcelModel {

    private String code;

    private String creditCode;

    private String type;

    private String productCode;


}
