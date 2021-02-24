package com.calm.auth.handle;

import com.calm.auth.support.CalmAuth;
import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
public class AuthLogoutHandler implements LogoutSuccessHandler{
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("退出登录成功了---------进入退出登陆成功处理器！！！");
//        AuthUserUtil.logoutHandle(request, response, authentication);
//        response.sendRedirect(CalmAuth.LOGIN_URL);
        RequestUtils.setResponse(response, JsonResult.fail("退出登录成功"));
    }
}
