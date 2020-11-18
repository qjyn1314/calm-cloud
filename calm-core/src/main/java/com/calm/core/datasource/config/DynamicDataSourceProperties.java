package com.calm.core.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据源变量名
 *
 * @author wangjunming
 * @since 2020/10/31 20:17
 */
@Data
@Primary
@Configuration
@ConfigurationProperties("spring.datasource")
public class DynamicDataSourceProperties {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * jdbcurl
     */
    private String url;

    /**
     * 驱动类型
     */
    private String driverClassName;

    /**
     * 查询数据源的SQL
     */
    private String queryDsSql = "select * from gen_datasource_conf where del_flag = 0";

}
