package com.calm.auth.service;

import com.calm.common.auth.CurrentUser;
import com.calm.parent.base.JsonResult;
import com.calm.parent.config.redis.RedisHelper;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    private RedisHelper redisHelper;

    @Override
    public CurrentUser loadUserByUsername(String account) throws UsernameNotFoundException {
        if (StringUtils.isBlank(account)) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        Object redisUser = redisHelper.getValue(RedisHelper.USER_KEY + account);
        if (Objects.nonNull(redisUser) && redisUser instanceof SysUserVo) {
            log.info("Get users from Redis");
            CurrentUser currentUser = new CurrentUser();
            preCurrentUser(currentUser, (SysUserVo) redisUser);
            return currentUser;
        }
        JsonResult<SysUserVo> jsonResult = userFeignService.queryByAccount(account);
        SysUserVo user = jsonResult.getData();
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        log.info("Get users from interface");
        redisHelper.valuePut(RedisHelper.USER_KEY + account, user);
        CurrentUser currentUser = new CurrentUser();
        preCurrentUser(currentUser, user);
        return currentUser;
    }

    public void preCurrentUser(CurrentUser currentUser, SysUserVo userVo) {
        currentUser.setUserId(userVo.getUserId());
        currentUser.setAccount(userVo.getAccount());
        currentUser.setPassword(userVo.getPassword());
        currentUser.setAvatar(userVo.getAvatar());
        currentUser.setSalt(userVo.getSalt());
        currentUser.setName(userVo.getName());
        currentUser.setBirthday(userVo.getBirthday());
        currentUser.setSex(userVo.getSex());
        currentUser.setEmail(userVo.getEmail());
        currentUser.setPhone(userVo.getPhone());
        currentUser.setStatus(userVo.getStatus());
        currentUser.setEnabled(UserStatus.THE_APPROVED.getCode().equals(userVo.getStatus()));
        currentUser.setRoles(Arrays.asList("system", "admin"));
    }


}
