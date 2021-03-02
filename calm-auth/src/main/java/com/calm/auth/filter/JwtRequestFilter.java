package com.calm.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.calm.auth.CurrentSecurityUserUtils;
import com.calm.auth.entity.CurrentUser;
import com.calm.auth.service.CalmUserService;
import com.calm.common.utils.RequestUtils;
import com.calm.parent.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/25 18:12
 */
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private final CalmUserService calmUserService;

    public JwtRequestFilter(CalmUserService calmUserService) {
        this.calmUserService = calmUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();
        log.info("JwtRequestFilter--url：{}", url);
        String token = request.getHeader(CurrentSecurityUserUtils.TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            log.info("将从cookie中获取用户token");
            Cookie cookie = RequestUtils.getCookieByName(request, CurrentSecurityUserUtils.TOKEN_NAME);
            if (null != cookie) {
                token = cookie.getValue();
            }
        }
        //验证token并将token验证成功的信息写入security的上下环境中
        if (StringUtils.isNotBlank(token)) {
            //判断token是否可以解析,并且这个token没有过期
            if (JwtUtils.parseToken(token) && !JwtUtils.isTokenExpired(token)) {
                String currentUserJson = JSONObject.toJSONString(JwtUtils.getClaimFromToken(token));
                CurrentUser currentUser = JSONObject.parseObject(currentUserJson, CurrentUser.class);
                CurrentUser userByUsername = calmUserService.loadUserByUsername(currentUser.getAccount());
                SecurityContext context = SecurityContextHolder.getContext();
                log.info("当前线程中的 SecurityContext：{}", context);
                //如果当前的security中是null则进行赋值否则不进行重新赋值当前登录用户信息
                if (null == context.getAuthentication()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userByUsername, null, userByUsername.getAuthorities());
                    context.setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
