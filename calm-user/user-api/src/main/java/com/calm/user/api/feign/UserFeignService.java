package com.calm.user.api.feign;

import com.calm.parent.base.JsonResult;
import com.calm.parent.config.FeignSupport;
import com.calm.parent.config.GlobalFeignConfig;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * explain: 用户服务暴露的服务
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 19:12
 */
@Component
@FeignClient(value = FeignSupport.USER_SERVICE,configuration = GlobalFeignConfig.class)
public interface UserFeignService {

    @PostMapping("/p/queryByAccount/{account}")
    JsonResult<SysUserVo> queryByAccount(@PathVariable("account") String account);

}
