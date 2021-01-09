package com.calm.gateway.controller;

import com.calm.gateway.config.GateProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @ApiOperation("请求测试动态的配置信息接口")
//    @GetMapping("/getGateInfo")
//    public String getNowDate() {
//        return gateProperties.toString();
//    }


}
