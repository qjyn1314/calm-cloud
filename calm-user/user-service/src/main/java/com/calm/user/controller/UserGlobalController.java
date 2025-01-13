package com.calm.user.controller;

import cn.hutool.core.date.DateUtil;
import com.calm.web.log.ReqLog;
import com.calm.parent.base.JsonResult;
import com.calm.user.consumer.UserConsumer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 22:49
 */
@Slf4j
@RestController
@RequestMapping("/global")
@Tag(name = "用户服务全局控制层")
public class UserGlobalController {

    @Autowired
    private UserConsumer userConsumer;

    @Operation(description = "获取当前时间")
    @GetMapping("/getNowTime")
    public JsonResult getNowTime() {
        return JsonResult.success(DateUtil.now());
    }

    @ReqLog
    @Operation(description = "获取用户表的自增序列信息")
    @GetMapping("/getSequence")
    public JsonResult getSequence() {
        String userSequence = userConsumer.getUserSequence();
        log.info("userSequence: {}", userSequence);
        return JsonResult.success(userSequence);
    }


}
