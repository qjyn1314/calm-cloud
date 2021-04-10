package com.calm.user.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 用户编码
     */
    private String code;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 备注
     */
    private String remarks;
    /**
    * 状态
    */
    private Integer status;
    /**
     * 权限编码
     */
    private List<String> roles;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 角色编码-逗号分隔
     */
    private String roleCode;
    /**
     * 角色名称-逗号分隔
     */
    private String roleName;

}