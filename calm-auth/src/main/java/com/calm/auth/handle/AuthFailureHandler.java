package com.calm.auth.handle;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * explain:登录失败处理器
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/21 10:35
 */
@Slf4j
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.info("【登录失败】" + exception.getMessage());
//        if (exception instanceof UsernameNotFoundException){
//            HulunBuirException.build(exception.getMessage());
//        }
//        if(exception instanceof NotActivationException){
//            HulunBuirException.build(exception.getMessage());
//        }
//        HulunBuirException.build("登录失败，请检查用户名和密码是否正确！");
//        response.sendRedirect(Auth.LOGIN_FAIL_URL);
    }
}
