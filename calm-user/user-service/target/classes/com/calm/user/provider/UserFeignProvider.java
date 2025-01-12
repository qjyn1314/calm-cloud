package com.calm.user.provider;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Tag(name = "对外提供的feign层-X")
public class UserFeignProvider {

    @Autowired
    private SysUserService service;

    /**
     * 获取当前登录用户信息,角色和权限
     *
     * @author wangjunming
     * @since 2021/2/24 14:25
     */
    @Operation(description = "获取当前登录用户信息,角色和权限")
    @PostMapping("/selectByCode/{code}")
    public JsonResult<Object> selectByCode(@PathVariable String code) {
        SysUserVo userVo = service.selectByCode(code);
        return JsonResult.success(userVo);
    }

}