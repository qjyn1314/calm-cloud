package com.calm.common.config;

import com.calm.common.auth.UserTokenThreadLocal;
import com.calm.parent.config.ForwardAccessService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * 此为设置feign的请求头信息，用设置服务之间的调用时所使用的用户token
 *
 * @author wangjunming
 * @since 2021/4/17 20:37
 */
@Slf4j
@Configuration
public class FeignAuthInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate request) {
        log.info("设置服务之间的调用时所使用的用户token");
        request.header(ForwardAccessService.TOKEN_NAME, UserTokenThreadLocal.getCurrentUserToken());
    }
}
