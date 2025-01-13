package com.calm.web.highway;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author wangjunming
 * @since 2024-06-28 17:20
 */
@Slf4j
@Component
public class UrlFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.warn("过滤器中的请求路径-getRequestURI->{}", request.getRequestURI());
        log.warn("过滤器中的请求路径-getPathInfo->{}", request.getPathInfo());
        log.warn("过滤器中的请求路径-getContextPath->{}", request.getContextPath());
        log.warn("过滤器中的请求路径-getServletPath->{}", request.getServletPath());

        com.calm.common.auth.UserTokenThreadLocal.setCurrentUserToken("AAAAAAA");

        filterChain.doFilter(request, response);

        com.calm.common.auth.UserTokenThreadLocal.clearCurrentUserToken();

        log.warn("过滤器请求执行完成-请求的URL->{}", request.getServletPath());

    }
}
