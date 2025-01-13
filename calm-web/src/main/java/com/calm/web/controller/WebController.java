package com.calm.web.controller;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.vo.SysUserVo;
import com.calm.web.consumer.WebConsumer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wangjunming
 * @since 2025-01-13 12:47
 */
@Slf4j
@RestController
@RequestMapping("/web-service")
@Tag(name = "web服务控制层")
public class WebController {

    @Autowired
    private WebConsumer webConsumer;

    @Operation(description = "获取当前时间")
    @GetMapping("/getUserByCode")
    public JsonResult<SysUserVo> getUserByCode(@RequestParam("code") String code) {
        SysUserVo sysUserVo = webConsumer.selectUserByCode(code);
        return JsonResult.success(sysUserVo);
    }

}
