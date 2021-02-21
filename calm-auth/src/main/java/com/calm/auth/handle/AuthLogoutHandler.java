package com.calm.auth.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain: 退出登录处理器
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/21 12:26
 */
@Slf4j
@Component
public class AuthLogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("退出登录成功了---------进入退出登陆成功处理器！！！");
//        AuthUserUtil.logoutHandle(request, response, authentication);
//        response.sendRedirect(Auth.LOGIN_URL);
    }
}
