package com.calm.common.config;

import com.alibaba.fastjson.JSONObject;
import com.calm.common.auth.CurrentUser;
import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import com.calm.parent.config.CalmProperties;
import com.calm.parent.config.ForwardAccessService;
import com.calm.parent.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain:
 * 1、SpringCloud确保服务只能通过gateway转发访问，禁止直接调用接口访问
 * 参考： https://blog.csdn.net/Hpsyche/article/details/102926010
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/30 14:23
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //如果是admin监控服务，或是前端项目 访问则放过请求，不需要验证请求头
        if (CalmProperties.isAdminService()) {
            return true;
        }
        //限制服务必须从网关进入
        if(!CalmProperties.isWebService()){
            String forwardAccessHeaderValue = request.getHeader(ForwardAccessService.HEADER_KEY);
            if (StringUtils.isBlank(forwardAccessHeaderValue) || !ForwardAccessService.HEADER_VALUE.equals(forwardAccessHeaderValue)) {
                RequestUtils.setResponse(response, JsonResult.fail("网关异常，请确认请求路径。"));
                return false;
            }
        }
        //url是否需要验证
        String url = request.getRequestURI();
        log.info("request url is ：{}", url);
        //如果是不需要登录用户访问，则放过请求
        if (ForwardAccessService.validateUrl(url)) {
            return true;
        }
        //获取token
        String token = request.getHeader(ForwardAccessService.TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            log.info("cookie中获取用户token");
            Cookie cookie = RequestUtils.getCookieByName(request, ForwardAccessService.TOKEN_NAME);
            if (null != cookie) {
                token = cookie.getValue();
            }
        }
        //需要验证token
        if (StringUtils.isBlank(token)) {
            RequestUtils.setResponse(response, JsonResult.fail("请登录。"));
            log.warn("未使用请求头或cookie中的token");
            return false;
        }
        //是否解析正确
        if (!JwtUtils.parseToken(token)) {
            RequestUtils.setResponse(response, JsonResult.fail("请登录。"));
            log.warn("解析请求头中的token失败");
            return false;
        }
        //是否过期
        if (JwtUtils.isTokenExpired(token)) {
            RequestUtils.setResponse(response, JsonResult.fail("请登录。"));
            log.warn("请求头中的token已过期");
            return false;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("当前线程中的 SecurityContext：{}", context);
        //如果当前的security中是null则进行赋值否则不进行重新赋值当前登录用户信息
        if (null == context.getAuthentication()) {
            //将当前登录用户的信息放入security中
            String currentUserJson = JSONObject.toJSONString(JwtUtils.getClaimFromToken(token));
            log.info("当前登录用户token信息是：{}",currentUserJson);
            CurrentUser currentUser = JSONObject.parseObject(currentUserJson, CurrentUser.class);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            context.setAuthentication(authentication);
        }
        log.info("当前线程中的--> SecurityContext：{}", context);
        return true;
    }

}
