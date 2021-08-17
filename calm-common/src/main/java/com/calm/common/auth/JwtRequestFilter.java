package com.calm.common.auth;

import com.calm.common.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("URL：--{}" , request.getRequestURI());
        log.info("HTTP_METHOD:--{}", request.getMethod());
        log.info("IP：--{}" , request.getRemoteAddr());
        String token = request.getHeader(CurrentSecurityUserUtils.TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            log.info("cookie get Authorization");
            Cookie cookie = RequestUtils.getCookieByName(request, CurrentSecurityUserUtils.TOKEN_NAME);
            if (null != cookie) {
                token = cookie.getValue();
            }
        }
        log.info("TOKEN____{}" , token);
        //验证token并将token验证成功的信息写入security的上下环境中
        if (StringUtils.isBlank(token)) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
        UserTokenThreadLocal.clearCurrentUserToken();
    }

}
