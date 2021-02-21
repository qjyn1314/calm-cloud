package com.calm.user.consumer;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * explain: 消费外部服务
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 22:10
 */
@RestController
@RequestMapping("/p")
public class UserConsumer {

    @Autowired
    private UserFeignService userFeignService;

    @ApiOperation("获取用户信息")
    @GetMapping("/consumer")
    public String consumer(){
        String account = "qjyn1314@163.com";
        JsonResult<SysUserVo> jsonResult = userFeignService.queryByAccount(account);
        SysUserVo data = jsonResult.getData();
        return data.getAccount();
    }


}
