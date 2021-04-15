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
        if (StringUtils.isBlank(account)) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        JsonResult<SysUserVo> result = userFeignService.queryByAccount(account);
        SysUserVo user = result.getData();
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        CurrentUser currentUser = new CurrentUser();
        preCurrentUser(currentUser, user);
        return currentUser;
    }

    public void preCurrentUser(CurrentUser currentUser, SysUserVo userVo) {
        BeanUtils.copyProperties(userVo,currentUser);
        currentUser.setEnabled(UserStatus.THE_APPROVED.getCode().equals(userVo.getStatus()));
    }


}
