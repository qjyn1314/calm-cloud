package com.calm.parent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
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
@EnableAspectJAutoProxy
@DependsOn({"applicationUtil"})
@ConfigurationProperties(prefix = "calm")
public class CalmProperties {

    private static final String SERVER_PORT = "server.port";
    private static final String APPLICATION_NAME = "spring.application.name";
    public static final String SERVERNAME_PORT = "SERVERNAME_PORT";
    public static final String WEB_SERVICE = "calm-web";
    public static final String ADMIN_SERVICE = "calm-admin";
    public static final String JOB_SERVICE = "calm-xxl-job";

    @Autowired
    private Environment environment;
    private static Environment ENVIRONMENT = null;
    private String interceptUrl;
    private String validationUrl;

    @PostConstruct
    public void init() {
        ENVIRONMENT = environment;
    }

    @Nullable
    public static String getPort() {
        return ENVIRONMENT.getProperty(SERVER_PORT);
    }

    @Nullable
    public static String getApplicationName() {
        return ENVIRONMENT.getProperty(APPLICATION_NAME);
    }

    public static Boolean isWebService() {
        return Objects.equals(getApplicationName(), WEB_SERVICE);
    }

    public static Boolean isAdminService() {
        return Objects.equals(getApplicationName(), ADMIN_SERVICE);
    }

    public static Boolean isJobService() {
        return Objects.equals(getApplicationName(), JOB_SERVICE);
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
