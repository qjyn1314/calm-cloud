package com.calm.core.datasource.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/3/30 11:58
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidPoolConfig extends DruidConfig {

}