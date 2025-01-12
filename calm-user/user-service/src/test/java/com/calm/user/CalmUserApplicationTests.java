package com.calm.user;

import cn.hutool.json.JSONUtil;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CalmUserApplicationTests {
    @Autowired
    private SysUserService userService;

    @Test
    void validatePassword() {
        String account = "qjyn1390@163.com";
        String password = "admin";
        SysUserVo sysUserVo = userService.validatePassword(account, password);
        log.info("validatePassword：{}", JSONUtil.toJsonStr(sysUserVo));
    }

}
