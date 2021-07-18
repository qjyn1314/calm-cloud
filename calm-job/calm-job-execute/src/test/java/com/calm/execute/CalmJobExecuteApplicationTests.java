package com.calm.execute;

import com.calm.sequence.api.enums.SequenceType;
import com.calm.sequence.api.feign.SequenceFeignService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CalmJobExecuteApplicationTests {
    @Autowired
    private SequenceFeignService sequenceFeignService;

    @Test
    void contextLoads() {
        log.info("调用的服务类是：{}",sequenceFeignService);
        Integer length = 5;
        String sequenceNum = sequenceFeignService.getSequenceNum(SequenceType.XXL_JOB.getCode(), length);
        log.info("生成的序列号是：{}",sequenceNum);
    }

}
