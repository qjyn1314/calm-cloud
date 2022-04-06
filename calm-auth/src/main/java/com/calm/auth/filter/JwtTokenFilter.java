package com.calm.auth.filter;

import com.calm.auth.service.CalmUserServiceImpl;
import com.calm.common.auth.AuthUserDetail;
import com.calm.common.auth.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class JwtTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private final CalmUserServiceImpl calmUserServiceImpl;

    public JwtTokenFilter(CalmUserServiceImpl calmUserServiceImpl, BCryptPasswordEncoder passwordEncoder) {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(AuthUserDetail.FORM_LOGIN_URL, HttpMethod.POST.name());
        this.setRequiresAuthenticationRequestMatcher(antPathRequestMatcher);
        this.calmUserServiceImpl = calmUserServiceImpl;
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
        log.info("Get user from interface");
        CurrentUser userDetails = calmUserServiceImpl.loadUserByUsernameAndPassword(username,password);
        log.info("The login_success user message is：{}", userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

}
