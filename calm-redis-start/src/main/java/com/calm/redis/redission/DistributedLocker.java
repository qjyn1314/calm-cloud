package com.calm.redis.redission;

import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁接口类
 *
 * @author wangjunming@zhichubao.com 2021/9/27 16:57
 */
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

    RCountDownLatch getCountDownLatch(String name);

    RSemaphore getSemaphore(String name);
}
