package com.calm.web.controller;

import com.calm.parent.base.JsonResult;
import com.calm.provider.entitys.OrderFeignEntity;
import com.calm.provider.service.OrderFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    /**
     * 测试分布式事务的步骤:
     *
     * 1.客户端从web工程传参进入
     * 2.调用order工程创建订单信息
     * 3.调用storage工程扣减库存
     * 4.调用pay工程进行扣减余额
     * 5.返回是否成功
     *
     * @author wangjunming
     * @since 2020/11/7 10:21
     */
    @ApiOperation("测试分布式事务")
    @GetMapping("/testDistributedTransaction")
    public JsonResult testDistributedTransaction() {
        OrderFeignEntity orderFeignEntity = new OrderFeignEntity();
        return orderFeignService.testDistributedTransaction(orderFeignEntity);
    }


}
