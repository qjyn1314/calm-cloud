package com.calm.parent.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2020/10/19 15:47
 */
@Configuration
public class FeignLogConfig {
    /**
     * 配置feign的增强打印日志
     *
     * @author wangjunming
     * @since 2020/10/19 15:48
     */
    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }






}
