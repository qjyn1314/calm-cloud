package com.calm.user.consumer;

import com.calm.sequence.api.feign.SequenceFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * explain: 用于消费feign接口的服务，进行feign接口的统一的管理
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/17 21:37
 */
@Slf4j
@Component
public class UserConsumer {

    @Autowired
    private SequenceFeignService sequenceFeignService;

    public String getUserSequence() {
        String sequenceNum = sequenceFeignService.getSequenceNum("calm-user", 12);
        log.info("userSequence: {}", sequenceNum);
        return sequenceNum;
    }


}
