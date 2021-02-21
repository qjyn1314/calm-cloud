package com.calm.user.controller;

import cn.hutool.core.date.DateUtil;
import com.calm.parent.base.JsonResult;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/global")
public class UserGlobalController {

    @ApiOperation("获取当前时间")
    @GetMapping("/getNowTime")
    public JsonResult getNowTime(){
        return JsonResult.success(DateUtil.now());
    }


}
