package com.calm.auth.config;

import com.calm.auth.CurrentSecurityUserUtils;
import com.calm.auth.filter.JwtRequestFilter;
import com.calm.auth.filter.JwtTokenFilter;
import com.calm.auth.handle.*;
import com.calm.auth.service.CalmUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    /**
     * 未授权拦截器
     */
    @Bean
    public UnAuthenticationEntryPoint unAuthenticationEntryPoint() {
        return new UnAuthenticationEntryPoint();
    }

    /**
     * 登陆成功处理器
     */
    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    /**
     * 登陆成功后，访问未授权接口
     */
    @Bean
    public UnAuthenticationHandler unAuthenticationHandler() {
        return new UnAuthenticationHandler();
    }

    /**
     * 登录失败处理器
     */
    @Bean
    public AuthFailureHandler authFailureHandler() {
        return new AuthFailureHandler();
    }

    /**
     * 退出登录成功处理器
     */
    @Bean
    public AuthLogoutHandler authLogoutHandler() {
        return new AuthLogoutHandler();
    }

    /**
     * 密码处理类
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * security中的用户信息
     */
    @Bean
    public CalmUserService calmUserService() {
        return new CalmUserService();
    }

    /**
     * 自定义用户认证拦截器
     */
    @Bean
    public JwtTokenFilter jwtTokenFilter() throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(calmUserService(), passwordEncoder());
        jwtTokenFilter.setAuthenticationSuccessHandler(authSuccessHandler());
        jwtTokenFilter.setAuthenticationFailureHandler(authFailureHandler());
        jwtTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtTokenFilter;
    }

    /**
     * 自定义token认证拦截器，只要不是/login请求的则都需要在请求头上添加token
     */
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(calmUserService());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 身份验证管理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder manager) throws Exception {
        manager
                .userDetailsService(calmUserService())
                .passwordEncoder(passwordEncoder());
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
            "/favicon.ico",
            "/login", "/"
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
        //配置退出登录
        http.logout()
                .logoutUrl(CurrentSecurityUserUtils.LOGOUT_URL)
                //退出登录的处理--不能与logoutSuccessUrl同时使用，如果同时配置，则优先使用logoutSuccessUrl的配置。此配置主要是将pc端的cookie中用户信息进行清除
                .logoutSuccessHandler(authLogoutHandler())
                .and()
                //不需要登录认证的路径
                .authorizeRequests().antMatchers(MATCHERS_PERMIT_ALL).permitAll()
                //其余请求都需要登录认证通过
                .anyRequest().authenticated()
                //跨域
                .and().cors()
                //禁用跨站csrf攻击防御
                .and().csrf().disable();
        http.exceptionHandling()
                //登录但未授权拦截
                .accessDeniedHandler(unAuthenticationHandler())
                //未登录拦截
                .authenticationEntryPoint(unAuthenticationEntryPoint());
        //表示Spring Security永远不会创建一个session,也不会通过sessionID获取用户信息
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //关于springsecurity中过滤器的执行顺序详细讲解
        // 参考：1、https://www.cnblogs.com/summerday152/p/13635948.html
        //      2、https://blog.csdn.net/qq_36882793/article/details/102869583
        //在验证token之前进行验证
        http.addFilterBefore(jwtRequestFilter(), JwtTokenFilter.class);
        //在这里也就意味着，会先执行 jwtTokenFilter 再次执行 UsernamePasswordAuthenticationFilter
        http.addFilterAt(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.rememberMe();
    }


}
