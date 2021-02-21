package com.calm.user.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表(SysUser)实体类
 *
 * @author wangjunming
 * @since 2021-02-15 17:04:36
 */
@Data
public class SysUserVo implements Serializable {
    private static final long serialVersionUID = 246857797913472694L;
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

    /**
     * 权限编码
     */
    private List<String> roles;

}