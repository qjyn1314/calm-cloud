package com.calm.user.api.utils;

/**
 * <p>
 * explain: 当前登录用户工具类，用于在各个模块中获取当前登录用户
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:08
 */
public class CurrentSecurityUserUtils {

    public static boolean isLogin(){
        return Boolean.TRUE;
    }

    public static boolean isNotLogin(){
        return !isLogin();
    }

}
