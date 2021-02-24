package com.calm.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 * explain: 当前登录用户工具类，用于在各个模块中获取当前登录用户
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:08
 */
@Slf4j
@Configuration
public class CurrentSecurityUserUtils  {

    public static final String SUBJECT = "calm_security_subject";

    public static final Integer EXPIRE_SECS = 24;

    @Resource
    private PasswordEncoder encoderPassword;
    /**
     * 操作密码
     */
    private static PasswordEncoder passwordEncoder;
    /**
     * 初始化密码
     */
    public static String handleUser(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 获取Security全局信息
     */
    private static SecurityContext context() {
        return SecurityContextHolder.getContext();
    }

    /**
     * 被认证的主体的身份
     */
    private static Authentication authentication() {
        return context().getAuthentication();
    }

    /**
     * 当前security中的登录用户信息
     */
    private static Object principal() {
        return authentication().getPrincipal();
    }

    /**
     * 是否已登录
     */
    public static boolean isLogin(){
        return authentication().isAuthenticated();
    }

    /**
     * 没有登录
     */
    public static boolean isNotLogin(){
        return !isLogin();
    }

}
