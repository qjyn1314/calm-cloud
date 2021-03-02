package com.calm.auth.service;

import com.calm.auth.entity.CurrentUser;
import com.calm.parent.base.JsonResult;
import com.calm.parent.config.redis.RedisHelper;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

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
    @Resource
    private RedisHelper redisHelper;

    @Override
    public CurrentUser loadUserByUsername(String account) throws UsernameNotFoundException {
        if (StringUtils.isBlank(account)) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        Object redisUser = redisHelper.getValue(RedisHelper.USER_KEY + account);
        if(!Objects.isNull(redisUser) && redisUser instanceof SysUserVo){
            CurrentUser currentUser =new CurrentUser();
            currentUser.preCurrentUser((SysUserVo)redisUser);
            return currentUser;
        }
        JsonResult<SysUserVo> jsonResult = userFeignService.queryByAccount(account);
        SysUserVo user = jsonResult.getData();
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        redisHelper.valuePut(RedisHelper.USER_KEY + account,user);
        CurrentUser currentUser =new CurrentUser();
        currentUser.preCurrentUser(user);
        return currentUser;
    }
}
