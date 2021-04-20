package com.calm.user.api.feign;

import com.calm.parent.base.JsonResult;
import com.calm.parent.config.FeignSupport;
import com.calm.parent.config.GlobalFeignConfig;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * explain: 用户服务暴露的服务
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 19:12
 */
@Component
@FeignClient(value = FeignSupport.USER_SERVICE, configuration = GlobalFeignConfig.class)
public interface UserFeignService {

    /**
     * 授权服务调用验证密码，并返回登录用户的信息
     * 
     * @param account 登录账号
     * @param password 输入的密码
     * @author wangjunming
     * @since 2021/4/17 22:13
     * @return com.calm.parent.base.JsonResult<com.calm.user.api.vo.SysUserVo>
     */
    @PostMapping("/p/validatePassword/{account}/{password}")
    JsonResult<SysUserVo> validatePassword(@PathVariable("account") String account, @PathVariable("password") String password);

    /**
     * 提供通过当前登录用户编码获取用户信息
     *
     * @param code 用户编码
     * @author wangjunming
     * @since 2021/4/17 22:14
     * @return com.calm.parent.base.JsonResult<com.calm.user.api.vo.SysUserVo>
     */
    @PostMapping("/x/selectByCode/{code}")
    JsonResult<SysUserVo> selectByCode(@PathVariable("code") String code);

}
