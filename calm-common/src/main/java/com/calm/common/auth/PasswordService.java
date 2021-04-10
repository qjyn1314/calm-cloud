package com.calm.common.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 20:25
 */
public class PasswordService {

    /**
     * 生成密码
     *
     * @param rawPassword 明文密码
     * @param salt 盐值
     * @author wangjunming
     * @since 2021/4/10 20:30
     * @return java.lang.String
     */
    public static String encode(CharSequence rawPassword,String salt) {
        //根据 明文密码 ，盐（随机） 然后生成加密密码
        return BCrypt.hashpw(rawPassword.toString(), salt);
    }
    /**
     * 获取随机盐值
     *
     * @author wangjunming
     * @since 2021/4/10 20:30
     * @return java.lang.String
     */
    public static String getSalt(){
        return BCrypt.gensalt(5);
    }


}
