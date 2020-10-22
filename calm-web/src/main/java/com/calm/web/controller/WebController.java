package com.calm.web.controller;

import com.calm.provider.service.OrderFeignService;
import io.swagger.annotations.Api;
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
 * @since 2020/9/11 17:37
 */
@Api(tags = "Web服务中的控制层")
@RestController
public class WebController {

    @Autowired
    private OrderFeignService orderFeignService;

    @ApiOperation("请求测试feign接口")
    @GetMapping("/getNowDate")
    public String getNowDate() {
        return orderFeignService.testFeignInterface();
    }


}
