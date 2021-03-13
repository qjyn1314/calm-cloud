package com.calm.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * explain: springmvc配置类，
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/16 10:57
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authIntercept;
    /**
     * 拦截器的添加
     *
     * @author wangjunming
     * @since 2021/2/12 23:06
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercept).addPathPatterns("/**");
    }

    /**
     * 跨域支持
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 允许跨域访问的路径
//        registry.addMapping("/**")
//                // 允许跨域访问的源
//                .allowedOrigins("*")
//                // 允许请求方法
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                // 预检间隔时间
//                .maxAge(168000)
//                // 允许头部设置
//                .allowedHeaders("*")
//                // 是否发送cookie
//                .allowCredentials(true);
//    }

}