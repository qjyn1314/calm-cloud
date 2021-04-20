package com.calm.common.auth;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * explain: 用户密码工具类
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 20:25
 */
public class PasswordService {

    /**
     * 生成密码--注意：rawPassword = rawPassword + salt  ； salt = salt  ；
     *
     * @param rawPassword 明文密码
     * @param salt        盐值
     * @return java.lang.String
     * @author wangjunming
     * @since 2021/4/10 20:30
     */
    public static String encode(CharSequence rawPassword, String salt) {
        //根据 明文密码 ，盐（随机） 然后生成加密密码
        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

    /**
     * 获取随机盐值
     *
     * @return java.lang.String
     * @author wangjunming
     * @since 2021/4/10 20:30
     */
    public static String getSalt() {
        return BCrypt.gensalt(5);
    }

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 登录密码的验证
     *
     * @param password        表单输入的密码
     * @param salt            数据库中的盐值
     * @param correctPassword 数据库中的密码
     * @author wangjunming
     * @since 2021/4/16 10:35
     */
    public static void validatePassword(String password, String salt, String correctPassword) {
        //密码验证参考：https://blog.csdn.net/qq_39009944/article/details/104388335
        password = password + salt;
        boolean matches = PASSWORD_ENCODER.matches(password, correctPassword);
        if (!matches) {
            throw new AuthenticationServiceException("密码不正确。");
        }
    }


    public static void main(String[] args) {
        //登录密码
        String password = "admin";
        //盐值
        String salt = getSalt();
        System.out.println("salt:"+salt);
        //密文密码
        String encodePassword = encode(password+salt,salt);
        System.out.println("encodePassword:"+encodePassword);
    }

}
