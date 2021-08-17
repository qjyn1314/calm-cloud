package com.calm.gateway.filter;

import com.calm.parent.config.ForwardAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 * explain:
 * 1、SpringCloud确保服务只能通过gateway转发访问，禁止直接调用接口访问
 * 参考： https://blog.csdn.net/Hpsyche/article/details/102926010
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:11
 */
@Slf4j
@Component
public class ValidationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        log.info("Gateway Request Url --：{}", request.getURI());
        String authorization = request.getHeaders().getFirst(ForwardAccessService.TOKEN_NAME);
        log.info("Authorization--{}", authorization);
        ServerHttpRequest req = exchange.getRequest().mutate()
                //所有服务必须从网关进行访问，除却有特殊服务，在 AuthInterceptor  中获取当前请求的服务名进行放过请求
                .header(ForwardAccessService.HEADER_KEY, ForwardAccessService.HEADER_VALUE)
                //设置用户的token
                .header(ForwardAccessService.TOKEN_NAME, authorization)
                .build();
        return chain.filter(exchange.mutate().request(req).build());
    }

    @Override
    public int getOrder() {
        return 5;
    }

}
