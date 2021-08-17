package com.calm.user.provider;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * explain:对外提供服务必须有用户的jwtToken
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 20:55
 */
@RestController
@RequestMapping("/x")
public class UserFeignProvider {

    @Autowired
    private SysUserService service;

    /**
     * 获取当前登录用户信息,角色和权限
     *
     * @author wangjunming
     * @since 2021/2/24 14:25
     */
    @ApiOperation("获取当前登录用户信息,角色和权限")
    @PostMapping("/selectByCode/{code}")
    public JsonResult<Object> selectByCode(@PathVariable String code) {
        SysUserVo userVo = service.selectByCode(code);
        return JsonResult.success(userVo);
    }

}