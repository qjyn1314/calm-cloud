package com.calm.gen.excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * explain: 读取excel返回的值信息
 * </p>
 *
 * @author wangjunming@zhichubao.com  2021/8/5 12:09
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ExcelExportVO<T> {

    /** 附件总记录数 */
    private Integer sumCount;
    /** 成功导入记录数 */
    private Integer successCount;
    /** 成功导入的数据 */
    private List<T> successList;
    /** 失败记录数 */
    private Integer failCount;
    /** 失败记录数据 */
    private List<T> failList;
    /** 错误行数集合 */
    private List<Integer> errorLineNumbers;
    /** 错误的excel文件路径*/
    private String failFilePath;


}
