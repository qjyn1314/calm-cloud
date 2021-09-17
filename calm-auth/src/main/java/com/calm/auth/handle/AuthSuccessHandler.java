package com.calm.auth.handle;

import com.alibaba.fastjson.JSONObject;
import com.calm.common.auth.AuthUserDetail;
import com.calm.common.auth.CurrentUser;
import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import com.calm.parent.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * explain:登陆成功处理器
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/21 10:33
 */
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("login success");
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        //将用户信息转换成一个token
        String userJson = JSONObject.toJSONString(currentUser);
        Map hashMap = JSONObject.parseObject(userJson, Map.class);
        String token = JwtUtils.generateToken(hashMap, AuthUserDetail.SUBJECT, AuthUserDetail.EXPIRE_SECS);
        log.info("token:{}", token);
        request.setAttribute(AuthUserDetail.TOKEN_NAME,token);
        response.setHeader(AuthUserDetail.TOKEN_NAME,token);
        RequestUtils.setCookie(response, AuthUserDetail.TOKEN_NAME,token, AuthUserDetail.EXPIRE_SECS_COOKIE, AuthUserDetail.COOKIE_DOMAIN, AuthUserDetail.COOKIE_PATH);
        RequestUtils.setResponse(response, JsonResult.successDataMsg(token, "登陆成功"));
    }
}
