package com.calm.user.api.dto;

import com.calm.parent.base.BaseDto;
import com.calm.user.api.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(SysUser)实体类
 *
 * @author wangjunming
 * @since 2021-02-15 17:04:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserDto extends BaseDto implements Serializable {
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
    @Email(message = "请输入正确的电子邮箱格式。")
    @Length(min = 1,message = "账号不能为空。")
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
    * 新密码
    */
    private String changePassword;
    /**
    * 确认新密码
    */
    private String confirmPassword;
    /**
    * md5密码盐
    */
    private String salt;
    /**
    * 名字
    */
    @Length(max = 10, message = "姓名最大长度是10。")
    private String name;
    /**
    * 生日
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    /**
    * 性别:0-女；1-男；2-未知
    */
    private Integer sex;
    /**
    * 电子邮件
    */
    @Email(message = "电子邮箱格式不正确。")
    private String email;
    /**
    * 电话
    */
    @Length(max = 11,message = "手机号最大长度为11。")
    private String phone;
    /**
     * 备注
     */
    @Length(max = 200, message = "备注最大长度为200。")
    private String remarks;
    /**
    * 状态(字典)
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
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色编码逗号分隔的字符串
     */
    private String[] roleCodes;

    /**获取保存的用户信息*/
    public SysUser getSysUser() {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(this, sysUser);
        return sysUser;
    }

    /**获取更新的用户信息*/
    public SysUser getUpdateSysUser() {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(this, sysUser,"createTime","createUser");
        return sysUser;
    }

}