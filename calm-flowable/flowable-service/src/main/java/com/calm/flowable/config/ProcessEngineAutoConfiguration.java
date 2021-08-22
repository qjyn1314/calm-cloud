package com.calm.flowable.config;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * <p>
 * explain: 配置出flowable流程引擎配置类，默认使用master数据库
 * </p>
 *
 * @author wangjunming  2021/8/15 20:27
 */
@Slf4j
@DS("#master")
@Configuration
public class ProcessEngineAutoConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceTransactionManager sourceTransactionManager;

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        //使用什么数据库
        configuration.setDataSource(dataSource);
        //使用的事务管理器
        configuration.setTransactionManager(sourceTransactionManager);
        //配置进行更新表信息
        configuration.setDatabaseSchemaUpdate(Boolean.TRUE.toString());
        return configuration;
    }

}
