package com.calm.auth.service;

import com.calm.auth.entity.CurrentUser;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:37
 */
public class CalmUserService implements UserDetailsService {

    private UserFeignService userFeignService;

    @Autowired
    public void setUserFeignService(UserFeignService userFeignService) {
        this.userFeignService = userFeignService;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        JsonResult<SysUserVo> jsonResult = userFeignService.queryByAccount(account);
        SysUserVo userVo = jsonResult.getData();
        if(null == userVo){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        CurrentUser currentUser = new CurrentUser();
        currentUser.preCurrentUser(userVo);
        return currentUser;
    }
}
