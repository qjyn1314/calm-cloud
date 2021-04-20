package com.calm.common.auth;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * explain: 用于存储当前登录用户的jwt-Token,将在服务之间的feign接口中也必须要登录,此为设置token
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/15 18:49
 */
@Slf4j
public class UserTokenThreadLocal {

    private static final ThreadLocal<String> USER_TOKEN_CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 获取当前用户jwt-Token
     */
    public static String getCurrentUserToken() {
        return USER_TOKEN_CONTEXT_HOLDER.get();
    }

    /**
     * 设置当前用户jwt-Token
     */
    public static void setCurrentUserToken(String userToken) {
        log.info("设置jwt-Token：" + userToken);
        USER_TOKEN_CONTEXT_HOLDER.set(userToken);
    }

    /**
     * 清除当前的线程的usertoken
     */
    public static void clearCurrentUserToken() {
        log.info("清除jwt-Token....");
        USER_TOKEN_CONTEXT_HOLDER.remove();
    }


}
