package com.calm.parent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * <p>
 * explain: 公共的配置变量
 * </p>
 *
 * @author wangjunming
 * @since 2020/8/27 10:26
 */
@DependsOn("applicationUtil")
@Configuration
@ConfigurationProperties(prefix = "spring")
public class SpringProperties {

    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
