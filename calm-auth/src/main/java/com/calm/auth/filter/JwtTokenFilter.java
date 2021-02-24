package com.calm.auth.filter;

import com.calm.auth.entity.CurrentUser;
import com.calm.auth.service.CalmUserService;
import com.calm.auth.support.CalmAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    public JwtTokenFilter(CalmUserService calmUserService, PasswordEncoder passwordEncoder) {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(CalmAuth.LOGIN_URL, HttpMethod.POST.name());
        this.setRequiresAuthenticationRequestMatcher(antPathRequestMatcher);
        this.calmUserService = calmUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        log.info("当前正在登录的用户是：{}", username);
        String password = request.getParameter("password");
        CurrentUser userDetails = calmUserService.loadUserByUsername(username);
        validateUserPassword(password, userDetails);
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    private void validateUserPassword(String password, CurrentUser userDetails) {
        //密码验证参考：https://blog.csdn.net/qq_39009944/article/details/104388335
        password = password + userDetails.getSalt();
        boolean matches = this.passwordEncoder.matches(password, userDetails.getPassword());
        if (!matches) {
            throw new AuthenticationServiceException("密码不正确。");
        }
    }

}
