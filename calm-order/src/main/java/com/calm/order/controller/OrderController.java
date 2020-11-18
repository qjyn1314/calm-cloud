package com.calm.order.controller;

import com.calm.order.persistence.service.IOrdersService;
import com.calm.parent.base.JsonResult;
import com.calm.parent.config.CalmProperties;
import com.calm.provider.entitys.OrderFeignEntity;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/11 17:37
 */
@Api(tags = "order服务中的控制层")
@Slf4j
@RestController
public class OrderController {

    @GetMapping("/test")
    public String testFeignInterface() {
        log.info("-端口号：" + CalmProperties.getPort());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "-端口号：" + CalmProperties.getPort();
    }
    @Autowired
    private IOrdersService service;

    /**
     * 创建订单
     * @param orderFeignEntity
     * @return
     */
    @PostMapping("/testDistributedTransaction")
    public JsonResult testDistributedTransaction(@RequestBody OrderFeignEntity orderFeignEntity) {
        log.info("创建订单-端口号：" + CalmProperties.getPort());
        return JsonResult.success(service.testDistributedTransaction(orderFeignEntity));
    }

}
