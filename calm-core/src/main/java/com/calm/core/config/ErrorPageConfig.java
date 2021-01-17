package com.calm.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Explain:公共的配置类，
 *
 * </p >
 *
 * @author wangjunming
 * @since 2020-02-12 11:47
 */
@Configuration
public class ErrorPageConfig extends HandlerInterceptorAdapter {
    private final List<Integer> errorCodeList = Arrays.asList(404, 403, 500, 501);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        /*
         * 配置默认错误页面
         * @since 2020/11/1 18:24
         */
        if (errorCodeList.contains(response.getStatus())) {
            response.sendRedirect("/error/404");
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}