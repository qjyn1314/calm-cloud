package com.calm.parent.config;

import com.calm.parent.config.ForwardAccessService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * explain: 每一个feign服务必须要加入此配置类
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:30
 */
@Configuration
public class GlobalFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate request) {
        request.header(ForwardAccessService.HEADER_KEY,ForwardAccessService.HEADER_VALUE);
    }
}
