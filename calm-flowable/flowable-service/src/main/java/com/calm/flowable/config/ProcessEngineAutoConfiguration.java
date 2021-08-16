package com.calm.flowable.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * <p>
 * explain: 配置出flowable的初始化脚本类，
 * </p>
 *
 * @author wangjunming  2021/8/15 20:27
 */
@Configuration
public class ProcessEngineAutoConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceTransactionManager sourceTransactionManager;

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(sourceTransactionManager);
        configuration.setDatabaseSchemaUpdate(Boolean.TRUE.toString());
        return configuration;
    }

}
