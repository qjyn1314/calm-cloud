package com.calm.auth.entity;

import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * explain: 当前登录用户信息
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/14 20:03
 */
public class CurrentUser implements UserDetails {

    private String username;

    private String password;

    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(String.join(",",this.roles));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.enable;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void preCurrentUser(SysUserVo userVo) {
        this.username = userVo.getAccount();
        this.password = userVo.getPassword();
        this.enable = UserStatus.THE_APPROVED.getCode().equals(userVo.getStatus());
        this.roles = Arrays.asList("system","admin");
    }

}
