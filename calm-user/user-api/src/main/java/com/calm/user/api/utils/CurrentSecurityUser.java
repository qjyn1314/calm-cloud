package com.calm.user.api.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * explain: 当前登录用户
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:07
 */
@Data
public class CurrentSecurityUser implements Serializable {

    /**
     * 主键id
     */
    private Long userId;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 账号-唯一标识
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别(字典)
     */
    private Integer sex;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 状态(字典)
     */
    private Integer status;

}
