package com.calm.gen.excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * explain: 导入excel返回的值信息
 * </p>
 *
 * @author wangjunming@zhichubao.com  2021/8/5 12:09
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ExcelImportVO<T> {

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
    /** excel导入的错误信息 */
    private List<ExcelRes> errorRes;

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @Accessors(chain = true)
    static class ExcelRes {
        /**错误的行数*/
        private Integer lineNumber;
        /**错误的标题*/
        private String title;
        /**错误信息*/
        private String errorMsg;
    }

}
