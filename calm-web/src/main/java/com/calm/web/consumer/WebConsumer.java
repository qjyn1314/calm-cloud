package com.calm.web.consumer;

import com.calm.parent.base.JsonResult;
import com.calm.user.api.feign.UserFeignService;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * explain: 用于消费feign接口的服务，进行feign接口的统一的管理
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/17 21:44
 */
@Component
public class WebConsumer {

    @Autowired
    private UserFeignService userFeignService;

    /**
     * 通过当前登录用户编码查询用户信息以及菜单
     *
     * @param code 当前登录用户的编码
     * @author wangjunming
     * @since 2021/4/17 21:47
     * @return com.calm.user.api.vo.SysUserVo
     */
    public SysUserVo selectUserByCode(String code) {
        return userFeignService.selectByCode(code).getData();
    }


}
