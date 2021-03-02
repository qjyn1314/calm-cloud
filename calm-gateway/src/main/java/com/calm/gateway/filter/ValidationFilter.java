package com.calm.gateway.filter;

import com.calm.parent.config.ForwardAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
        RequestPath path = exchange.getRequest().getPath();
        boolean startsWithWeb = path.value().startsWith("/web");
        if (!startsWithWeb) {
            ServerHttpRequest req = exchange.getRequest().mutate()
                    .header(ForwardAccessService.HEADER_KEY, ForwardAccessService.HEADER_VALUE).build();
            return chain.filter(exchange.mutate().request(req.mutate().build()).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
