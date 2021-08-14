package com.calm.gen.excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * explain: excel导入导出配置表
 * </p>
 *
 * @author wangjunming@zhichubao.com  2021/8/6 22:07
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExcelSet {

    private Long id;
    /***
     * 标题头部
     */
    private String title;
    /***
     * 标题头部批注
     */
    private String annotation;
    /***
     * 是否必填
     */
    private Boolean required = false;

}
