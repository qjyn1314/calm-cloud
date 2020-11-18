package com.calm.provider.service;

import com.calm.parent.base.JsonResult;
import com.calm.provider.constant.FeignInterfaceConstant;
import com.calm.provider.entitys.OrderFeignEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * explain: order服务的配置
 * </p>
 *
 * @author wangjunming
 * @since 2020/10/19 16:14
 */
@FeignClient(name = FeignInterfaceConstant.ORDER_SERVER, value = FeignInterfaceConstant.ORDER_SERVER)
public interface OrderFeignService {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String testFeignInterface();

    @RequestMapping(value = "/testDistributedTransaction", method = RequestMethod.POST)
    JsonResult testDistributedTransaction(@RequestBody OrderFeignEntity orderFeignEntity);

}
