package com.calm.gateway.controller;

import com.calm.gateway.config.GateProperties;
import com.calm.user.api.feign.UserFeignService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/1/1 13:12
 */
@RestController
public class GateWayController {

    @Autowired
    private GateProperties gateProperties;

    @Autowired
    private UserFeignService userFeignService;

//    @PreAuthorize("hasAnyAuthority('admin','system')")
    @ApiOperation("请求测试动态的配置信息接口")
    @GetMapping("/getGateInfo")
    public String getNowDate() {
        final ServerHttpRequest request = (ServerHttpRequest)RequestContextHolder.currentRequestAttributes();
        return gateProperties.toString();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/getUserByAccount")
    public String getUserByAccount() {
        final ServerHttpRequest request = (ServerHttpRequest)RequestContextHolder.currentRequestAttributes();
        return gateProperties.toString();
    }

}
