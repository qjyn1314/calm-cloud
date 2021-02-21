package com.calm.auth.config;

import com.calm.auth.handle.AuthFailureHandler;
import com.calm.auth.handle.AuthLogoutHandler;
import com.calm.auth.handle.AuthSuccessHandler;
import com.calm.auth.service.CalmUserService;
import com.calm.auth.support.CalmAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * explain:
 * <p>
 * 本质上是由多个过滤器链组成的
 * FilterSecurityInterceptor  最后一个执行的过滤器
 * ExceptionTranslationFilter 异常抛出的过滤器
 * UsernamePasswordAuthenticationFilter 对login的post请求拦截，检验表单中的用户名和密码
 *
 * </p>
 * <p>
 * 学习视频达到第十五节：
 * <p>
 * https://www.bilibili.com/video/BV15a411A7kP?p=15
 *
 * @author wangjunming
 * @since 2021/2/11 17:11
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private AuthLogoutHandler authLogoutHandler;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CalmUserService calmUserService() {
        return new CalmUserService();
    }

    /**
     * 身份验证管理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManager) throws Exception {
        authenticationManager.userDetailsService(calmUserService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 静态资源的请求路径
     */
    private static final String[] IGNORING_MATCHERS = {"/css/**", "/error/**", "/static/**", "/instances/**", "/actuator/**",
            "/font/**", "/icon/**", "/images/**", "/js/**", "/json/**", "/layui/**", "/favicon.ico"};

    /**
     * 放过静态资源的请求
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(IGNORING_MATCHERS);
    }

    /**
     * 安全拦截放过的URL路径:"/p/**"-服务内部进行请求的接口
     */
    private static final String[] MATCHERS_PERMIT_ALL = {
            "/instances/**", "/instances", "/actuator/**", "/actuator/health", "/details",
            "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**",
            "/**/p/**",
            "/login", "/pc/login"
    };

    /**
     * 权限认证管理器
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //可以指定未授权时所跳转的路径，即自定义403页面
        http.exceptionHandling().accessDeniedPage("/index");
        //处理前端请求跨域的问题，参考：https://blog.csdn.net/davylee2008/article/details/61420751
        http.headers().frameOptions().sameOrigin();
        //配置表单登录
        http.formLogin()
//                //自定义form表单中input框name属性的值
//                .usernameParameter("username")
//                .passwordParameter("password")
//                //登录页路径
                .loginPage(CalmAuth.LOGINURL)
//                //指定登录form表单的请求路径
                .loginProcessingUrl(CalmAuth.LOGIN_FORM_URL)
//                //登陆成功的处理--不能与defaultSuccessUrl同时配置，如果同时配置，则优先使用defaultSuccessUrl的配置。此配置主要是将用户信息放置pc端的cookie中
                .successHandler(authSuccessHandler)
//                //登录失败的处理--不能与failureUrl/同时配置，如果同时配置，则优先使用failureUrl的配置。此配置主要是将用户的登陆失败原因返回给前端
                .failureHandler(authFailureHandler)
//                //认证成功成功后的请求路径
//                .defaultSuccessUrl(CalmAuth.LOGIN_SUCCESS_URL, true)
//                //认证失败的跳转
//                .failureUrl(Auth.LOGIN_FAIL_URL)
//                .and()
//                .logout()
//                .logoutUrl(Auth.LOGOUT_URL)
//                //退出登录的处理--不能与logoutSuccessUrl同时使用，如果同时配置，则优先使用logoutSuccessUrl的配置。此配置主要是将pc端的cookie中用户信息进行清除
//                .logoutSuccessHandler(authLogoutHandler())
                //退出登录成功的跳转路径
//                    .logoutSuccessUrl(Auth.LOGIN_URL)
                //配置拦截的路径
                .and()
                //不需要登录认证的路径-之后将配置到配置文件中
                .authorizeRequests().antMatchers(MATCHERS_PERMIT_ALL).permitAll()
                //其余请求都需要登录认证通过
                .anyRequest().authenticated()
                //跨域
                .and().cors()
                //禁用跨站csrf攻击防御
                .and().csrf().disable();
        // 添加JWT过滤器
//                .addFilter(new AuthTokenFilter(authenticationManager()));
    }


}
