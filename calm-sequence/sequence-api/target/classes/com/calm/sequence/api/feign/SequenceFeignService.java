package com.calm.sequence.api.feign;

import com.calm.common.nacos.feign.FeignSupport;
import com.calm.common.nacos.feign.GlobalFeignConfig;
import com.calm.parent.log.ServerLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * explain: 用户服务暴露的服务
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 19:12
 */
@Component
@FeignClient(value = FeignSupport.SEQUENCE_SERVICE,configuration = GlobalFeignConfig.class)
public interface SequenceFeignService {

    @ServerLog
    @GetMapping("/p/getSequenceNum/{sequenceType}/{length}")
    String getSequenceNum(@PathVariable("sequenceType") String sequenceType, @PathVariable(name = "length") Integer length);

}
