package com.calm.common.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * explain: 当前登录用户信息
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/14 20:03
 */
@Data
@ToString
public class CurrentUser implements UserDetails, Serializable {

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
     * 状态(字典)
     */
    private Integer status;
    /**
     * 是否已启用
     */
    private Boolean enabled;
    /**
     * 角色编码-逗号分隔
     */
    private String roleCode;
    /**
     * 角色名称-逗号分隔
     */
    private String roleName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(String.join(",", this.roleCode));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CurrentUser noPwd() {
        this.password = null;
        this.salt = null;
        return this;
    }
}
