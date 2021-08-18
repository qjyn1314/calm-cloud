package com.calm.gateway.filter;

import com.calm.parent.config.ForwardAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 * explain: 做token该有的一系列操作
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:11
 */
@Slf4j
@Component
public class JwtTokenFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst(ForwardAccessService.TOKEN_NAME);
        log.info("Authorization--{}", authorization);
        ServerHttpRequest req = exchange.getRequest().mutate()
                //设置用户的token
                .header(ForwardAccessService.TOKEN_NAME, authorization)
                .build();
        return chain.filter(exchange.mutate().request(req).build());
    }

    @Override
    public int getOrder() {
        return 25;
    }

}
