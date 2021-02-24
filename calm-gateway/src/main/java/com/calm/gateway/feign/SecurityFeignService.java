package com.calm.gateway.feign;

import com.calm.provider.config.GlobalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/22 20:46
 */
@Component
@FeignClient(value = "${spring.application.name}",configuration = GlobalFeignConfig.class)
public class SecurityFeignService {







}
