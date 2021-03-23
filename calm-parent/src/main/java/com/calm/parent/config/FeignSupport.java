package com.calm.parent.config;

/**
 * <p>
 * explain: feign注解中的服务名称
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/24 23:54
 */
public interface FeignSupport {

    /**
     * 用户服务
     */
    String USER_SERVICE = "calm-user";
    /**
     * 前端web服务
     */
    String WEB_SERVICE = "calm-web";

}
