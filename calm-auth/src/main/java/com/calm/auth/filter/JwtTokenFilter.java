package com.calm.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.calm.auth.service.CalmUserService;
import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.parent.config.redis.RedisHelper;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.exception.HaveBeenforzenException;
import com.calm.user.api.exception.ToauditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/21 13:11
 */
@Slf4j
@Component
public class JwtTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private final CalmUserService calmUserService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RedisHelper redisHelper;

    public JwtTokenFilter(CalmUserService calmUserService, PasswordEncoder passwordEncoder) {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(CurrentSecurityUserUtils.FORM_LOGIN_URL, HttpMethod.POST.name());
        this.setRequiresAuthenticationRequestMatcher(antPathRequestMatcher);
        this.calmUserService = calmUserService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contextPath = request.getRequestURI();
        log.info("JwtTokenFilter--url：{}", contextPath);
        String username = request.getParameter("username");
        log.info("The user currently logging in is：{}", username);
        String password = request.getParameter("password");
        String redisUserNameKey = RedisHelper.USER_KEY + username;
        //从缓存中获取
        Object redisUser = redisHelper.getValue(redisUserNameKey);
        CurrentUser userDetails = null;
        if (Objects.nonNull(redisUser) && redisUser instanceof String) {
            log.info("Get user from redis");
            try {
                userDetails = JSONObject.parseObject(redisUser.toString(),CurrentUser.class);
            } catch (Exception e) {
                log.error("从redis中获取当前登录用户解析失败，将从数据库中获取。--",e);
                userDetails = null;
                redisHelper.deleteByKey(redisUserNameKey);
            }
        }
        if (null == userDetails) {
            log.info("Get user from interface");
            userDetails = calmUserService.loadUserByUsernameAndPassword(username,password);
        }
        //验证通过之后，放入缓存中，每次用户相关操作时，删除缓存中的用户信息
        redisHelper.valuePut(redisUserNameKey, JSONObject.toJSONString(userDetails));
        log.info("The login_success user is：{}", JSONObject.toJSONString(userDetails));
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    private void validateUserPassword(String password, CurrentUser userDetails) {
        //密码验证参考：https://blog.csdn.net/qq_39009944/article/details/104388335
        password = password + userDetails.getSalt();
        boolean matches = this.passwordEncoder.matches(password, userDetails.getPassword());
        if (!matches) {
            throw new AuthenticationServiceException("密码不正确。");
        }
        if (UserStatus.TO_AUDIT.getCode().equals(userDetails.getStatus())) {
            throw new ToauditException("账号待审核，请联系管理员处理。");
        }
        if (UserStatus.DISABLE.getCode().equals(userDetails.getStatus())) {
            throw new HaveBeenforzenException("账号已停用，请联系管理员处理。");
        }
    }

}
