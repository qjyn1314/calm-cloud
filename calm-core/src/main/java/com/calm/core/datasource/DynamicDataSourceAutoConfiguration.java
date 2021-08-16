package com.calm.core.datasource;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.calm.core.datasource.config.DynamicDataSourceProperties;
import com.calm.core.datasource.config.JdbcDynamicDataSourceProvider;
import com.calm.core.datasource.config.LastParamDsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态数据源切换配置,直接复制的pig-cloud的动态数据源配置类
 *
 * @author wangjunming
 * @since 2020/10/31 20:08
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

	@Autowired
	private DynamicDataSourceProperties dynamicDataSourceProperties;

	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider() {
		log.info("主数据源的配置信息是：{}", dynamicDataSourceProperties);
		return new JdbcDynamicDataSourceProvider(dynamicDataSourceProperties);
	}

	@Bean
	public DsProcessor dsProcessor() {
		return new LastParamDsProcessor();
	}

}
