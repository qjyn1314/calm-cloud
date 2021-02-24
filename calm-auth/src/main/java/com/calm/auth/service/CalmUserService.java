package com.calm.auth.service;

import com.calm.auth.entity.CurrentUser;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:37
 */
public class CalmUserService implements UserDetailsService {

    @Resource
    private UserFeignService userFeignService;

    @Override
    public CurrentUser loadUserByUsername(String account) throws UsernameNotFoundException {
        JsonResult<SysUserVo> jsonResult = userFeignService.queryByAccount(account);
        SysUserVo user = jsonResult.getData();
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        CurrentUser currentUser =new CurrentUser();
        currentUser.preCurrentUser(user);
        return currentUser;
    }
}
