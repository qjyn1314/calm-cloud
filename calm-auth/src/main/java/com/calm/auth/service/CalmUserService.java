package com.calm.auth.service;

import com.calm.common.auth.CurrentUser;
import com.calm.parent.base.JsonResult;
import com.calm.parent.config.redis.RedisHelper;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:37
 */
@Slf4j
public class CalmUserService implements UserDetailsService {

    @Resource
    private UserFeignService userFeignService;

    @Override
    public CurrentUser loadUserByUsername(String account) throws UsernameNotFoundException {
        return new CurrentUser();
    }
    
    public CurrentUser loadUserByUsernameAndPassword(String account,String password) throws UsernameNotFoundException {
        CurrentUser currentUser = loadUserByUsername(account);
        if (StringUtils.isBlank(account)) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        JsonResult<SysUserVo> result = userFeignService.validatePassword(account,password);
        SysUserVo user = result.getData();
        if(null == user){
            throw new AuthenticationServiceException(result.getMessage());
        }
        preCurrentUser(currentUser, user);
        return currentUser;
    }

    public void preCurrentUser(CurrentUser currentUser, SysUserVo userVo) {
        BeanUtils.copyProperties(userVo,currentUser);
        currentUser.setEnabled(UserStatus.THE_APPROVED.getCode().equals(userVo.getStatus()));
    }


}
