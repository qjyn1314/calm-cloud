package com.calm.parent.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <p>
 * explain: 每一个feign服务必须要加入此配置类
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:30
 */
@Slf4j
@Configuration
public class GlobalFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate request) {
        request.header(ForwardAccessService.HEADER_KEY,ForwardAccessService.HEADER_VALUE);
    }

    /**
     * 配置feign的增强打印日志
     *
     * @author wangjunming
     * @since 2020/10/19 15:48
     */
    @Bean
    Logger.Level feignLogger() {
        log.info("配置feign的增强打印日志....");
        return Logger.Level.FULL;
    }

}
