package com.calm.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignAuthInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate request) {
//        request.header("user_token",);
    }
}
