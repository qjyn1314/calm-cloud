package com.calm.provider.service;

import com.calm.provider.constant.FeignInterfaceConstant;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 * explain: order服务的配置
 * 整合feign中出现的问题：
 * https://blog.csdn.net/u012211603/article/details/84312709
 * </p>
 *
 * @author wangjunming
 * @since 2020/10/19 16:14
 */
@FeignClient(name = FeignInterfaceConstant.ORDER_SERVER,contextId = FeignInterfaceConstant.ORDER_SERVER_CONTEXT_ID)
public interface OrderFeignService {

}
