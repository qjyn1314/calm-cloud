package com.calm.user.api.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * 用户表(SysUser)实体类
 *
 * @author wangjunming
 * @since 2021-02-15 17:04:36
 */
@Data
public class SysUser implements Serializable {
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
    * 状态 UserStatus
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 创建人
    */
    private Long createUser;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 更新人
    */
    private Long updateUser;
    /**
    * 乐观锁
    */
    private Integer version;

}