package com.calm.auth.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义 p6spy sql输出格式
 *
 * @author wangjunming
 * @since 2020/5/25 14:48
 */
public class P6SpySqlFormatConfig implements MessageFormattingStrategy {

    /**
     * 日志格式化方式（打印SQL日志会进入此方法，耗时操作，生产环境不建议使用）
     *
     * @param connectionId: 连接ID
     * @param now:          当前时间
     * @param elapsed:      花费时间
     * @param category:     类别
     * @param prepared:     预编译SQL
     * @param sql:          最终执行的SQL
     * @param url:          数据库连接地址
     * @return 格式化日志结果
     **/
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql) ? "执行耗时 " + elapsed + " ms | SQL 语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : "";
    }
}
