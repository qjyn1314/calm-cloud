package com.calm.redis.delredis;

import com.calm.redis.service.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * explain: 将根据注解中的redis的key删除数据
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/18 15:40
 */
@Slf4j
@Aspect
@Component
public class DelRedisAspect {
    @Autowired
    private RedisHelper redisService;

    /**
     * 定义后置通知，在方法执行完成后，将redis中的数据进行删除
     *
     * @author wangjunming
     * @since 2020/12/18 16:31
     */
    @After("execution(public * com.calm.user.persistence.service.impl.SysUserServiceImpl.* (..)) && @annotation(delRedis)")
    public void after(DelRedis delRedis) {
        final String redisKey = delRedis.value();
        log.info("进入删除权限的aop中-redisKey是：{}", redisKey);
        final boolean delete = redisService.deleteByKey(redisKey);
        log.info("删除权限key是否成功：{}", delete);
    }

}
