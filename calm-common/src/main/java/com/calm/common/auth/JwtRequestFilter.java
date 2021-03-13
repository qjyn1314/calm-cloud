package com.calm.common.auth;

import com.alibaba.fastjson.JSONObject;
import com.calm.common.utils.RequestUtils;
import com.calm.parent.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain: 用于清除security中的用户信息，每次请求过来都会判断此token是否可以验证通过
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/25 18:12
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

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
        if (StringUtils.isNotBlank(token) && JwtUtils.parseToken(token) && !JwtUtils.isTokenExpired(token)) {
            //判断token是否可以解析,并且这个token没有过期
            String currentUserJson = JSONObject.toJSONString(JwtUtils.getClaimFromToken(token));
            CurrentUser currentUser = JSONObject.parseObject(currentUserJson, CurrentUser.class);
            SecurityContext context = SecurityContextHolder.getContext();
            log.info("JwtRequestFilter--SecurityContext：{}", context);
            //如果当前的security中是null则进行赋值否则不进行重新赋值当前登录用户信息
            if (null == context.getAuthentication()) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
                context.setAuthentication(authentication);
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

}
