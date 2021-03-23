package com.calm.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 * explain: 过滤器 filter
 * </p>
 *
 * @author wangjunming
 * @since 2020/10/20 10:56
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        log.info("进入AuthFilter-请求的路径是：{}", request.getURI());
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        log.info("进入AuthFilter-请求的参数是：{}", JSONObject.toJSONString(queryParams));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
