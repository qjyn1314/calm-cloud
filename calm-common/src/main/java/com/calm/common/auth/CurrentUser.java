package com.calm.common.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
     * 名字
     */
    private String username;
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

    /** 菜单 */
    private List<MenuTree> menuTree;

    /**
     * 账户是否过期
     */
    private Boolean accountNonExpired = Boolean.FALSE;

    /**
     * 帐户是否锁定
     */
    private Boolean accountNonLocked = Boolean.FALSE;

    /**
     * 凭证是否过期
     */
    private Boolean credentialsNonExpired = Boolean.FALSE;

    /**
     * 权限集合
     */
    private List<SimpleGrantedAuthority> authorities;

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

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

    /**账户是否过期*/
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    /**帐户是否锁定*/
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    /**凭证是否过期*/
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    public CurrentUser noPwd() {
        this.password = null;
        this.salt = null;
        return this;
    }
}
