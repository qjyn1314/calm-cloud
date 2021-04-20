package com.calm.user.provider;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * explain:此为不需要验证的服务接口，可通过postman直接获取数据
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 20:55
 */
@RestController
@RequestMapping("/p")
public class UserProvider {

    @Autowired
    private SysUserService service;

    /**
     * 获取当前登录用户信息
     *
     * @author wangjunming
     * @since 2021/2/24 14:25
     */
    @PostMapping("/validatePassword/{account}/{password}")
    public JsonResult<Object> validatePassword(@PathVariable String account,@PathVariable String password) {
        return JsonResult.success(service.validatePassword(account,password));
    }

}
