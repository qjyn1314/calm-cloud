package com.calm.job.controller;

import com.calm.sequence.api.feign.SequenceFeignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wangjunming
 * @since 2025-01-13 14:18
 */
@Slf4j
@RestController
@RequestMapping("/global")
@Tag(name = "定时任务控制层")
public class JobTestController {

    @Autowired
    private SequenceFeignService sequenceFeignService;

    @Operation(description = "获取用户服务的序列号")
    @GetMapping("/getUserSequence")
    public String getUserSequence() {
        String sequenceNum = sequenceFeignService.getSequenceNum("calm-user", 12);
        log.info("userSequence: {}", sequenceNum);
        return sequenceNum;
    }


}
