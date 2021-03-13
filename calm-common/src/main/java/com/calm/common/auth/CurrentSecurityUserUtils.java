package com.calm.common.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class CurrentSecurityUserUtils {

    /**token的主题*/
    public static final String SUBJECT = "calm_security_subject";
    /**header token key*/
    public static final String TOKEN_NAME = "user_token";
    /**24小时*/
    public static final Integer EXPIRE_SECS = 24;
    /**过期秒数*/
    public static final Integer EXPIRE_SECS_COOKIE = EXPIRE_SECS * 60 * 60;
    /**退出登录跳转的路径*/
    public static final String LOGIN_URL = "/";
    /**登录请求的路径*/
    public static final String FORM_LOGIN_URL = "/login";
    /**退出登录的请求路径*/
    public static final String LOGOUT_URL = "/logout";
    /**匿名用户的username*/
    public static final String ANONYMOUS_USER = "anonymousUser";
    /**登陆成功跳转的路径*/
    public static final String LOGIN_SUCCESS_URL = "/main";
    /**cookie设置的domain*/
    public static final String COOKIE_DOMAIN = "com";
    /**cookie设置的path*/
    public static final String COOKIE_PATH = "/";

    /**
     * 操作密码
     */
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 初始化密码
     */
    public static String handleUser(String password) {
        return PASSWORD_ENCODER.encode(password);
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
     * 获取当前登录用户信息
     */
    public static CurrentUser authUser() {
        return principal() instanceof UserDetails ? (CurrentUser) principal() : null;
    }

    /**
     * 是否已登录
     */
    public static boolean isLogin() {
        Authentication authentication = authentication();
        if(null== authentication){
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (ANONYMOUS_USER.equals(principal)) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /**
     * 没有登录
     */
    public static boolean isNotLogin() {
        return !isLogin();
    }

}
