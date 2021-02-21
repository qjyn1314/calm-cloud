package com.calm.user.provider;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * explain:对外提供服务
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

    @ApiOperation("根据名称获取用户信息")
    @GetMapping("/queryByAccount/{account}")
    public JsonResult<Object> queryByAccount(@PathVariable String account) {
        SysUserVo userVo = service.queryByAccount(account);
        return JsonResult.success(userVo);
    }

}
