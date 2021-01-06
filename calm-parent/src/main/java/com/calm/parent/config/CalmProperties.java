package com.calm.parent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * <p>
 * explain: 公共的配置变量
 * </p>
 *
 * @author wangjunming
 * @since 2020/8/27 10:26
 */
@Slf4j
@Configuration
@DependsOn({"applicationUtil"})
@ConfigurationProperties(prefix = "calm")
public class CalmProperties {

    private static final String SERVER_PORT = "server.port";
    private static final String APPLICATION_NAME = "spring.application.name";
    public static final String SERVERNAME_PORT = "SERVERNAME_PORT";

    @Autowired
    private Environment environment;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    private static Environment ENVIRONMENT = null;
    private String interceptUrl;
    private String validationUrl;

    @PostConstruct
    public void init() {
        ENVIRONMENT = environment;
        hashOperations.put(SERVERNAME_PORT, Objects.requireNonNull(getApplicationName()), Objects.requireNonNull(getPort()));
    }

    @Nullable
    public static String getPort() {
        return ENVIRONMENT.getProperty(SERVER_PORT);
    }
    @Nullable
    public static String getApplicationName() {
        return ENVIRONMENT.getProperty(APPLICATION_NAME);
    }

    public String getInterceptUrl() {
        return interceptUrl;
    }

    public void setInterceptUrl(String interceptUrl) {
        this.interceptUrl = interceptUrl;
    }

    public String getValidationUrl() {
        return validationUrl;
    }

    public void setValidationUrl(String validationUrl) {
        this.validationUrl = validationUrl;
    }
}
