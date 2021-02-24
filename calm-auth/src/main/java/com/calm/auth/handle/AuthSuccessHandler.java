package com.calm.auth.handle;

import com.alibaba.fastjson.JSONObject;
import com.calm.auth.CurrentSecurityUserUtils;
import com.calm.auth.entity.CurrentUser;
import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import com.calm.parent.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("登录成功了---------进入登陆成功处理器！！！");
        Object principal = authentication.getPrincipal();
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("SecurityContext:{}",context);
        RequestUtils.setResponse(response, JsonResult.successDataMsg(principal, "登陆成功"));
    }
}
