package com.calm.parent.config;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

/**
 * <p>
 * explain: 所有服务必须通过网关进行访问
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:10
 */
public class ForwardAccessService {

    public static String HEADER_KEY = "Calm_GateWay_Header_key";
    public static String HEADER_VALUE = "Calm_GateWay_Header_value";
    public static String TOKEN_NAME = "user_token";
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 不需要登录就可访问的静态文件路径
     * "/login"  登录接口
     * "/" 登录页面
     * "/p/**" feign中服务间的调用路径请求头
     */
    private static final String[] IGNORING_MATCHERS = {
            //服务中的请求路径
            "/login", "/","/p/**",
            //web服务中的静态资源
            "/css/**", "/error/**", "/static/**",
            "/font/**", "/icon/**", "/images/**", "/js/**", "/json/**", "/layui/**", "/favicon.ico",
            //springbootAdmin请求的服务信息接口
            "/instances/**", "/instances", "/actuator/**", "/details",
            //swagger请求资源
            "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**",
    };

    /**
     * 验证那些请求路径不需要token
     *
     * @param url 每次请求的路径URL
     * @return boolean
     */
    public static boolean validateUrl(String url) {
        return Arrays.stream(IGNORING_MATCHERS).anyMatch(unValidateUrl -> PATH_MATCHER.match(unValidateUrl, url));
    }

}
