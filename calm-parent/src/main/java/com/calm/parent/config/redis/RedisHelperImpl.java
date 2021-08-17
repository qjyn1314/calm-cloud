package com.calm.parent.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/3/1 13:43
 */
@Slf4j
@Service("redisHelper")
public class RedisHelperImpl<HK, T> implements RedisHelper<HK, T> {

    /**在构造器中获取redisTemplate实例, key(not hashKey) 默认使用String类型*/
    private final RedisTemplate<String, T> redisTemplate;
    /**在构造器中通过redisTemplate的工厂方法实例化操作对象*/
    private final HashOperations<String, HK, T> hashOperations;
    private final ListOperations<String, T> listOperations;
    private final ValueOperations<String, T> valueOperations;
    private final ZSetOperations<String, T> zSetOperations;
    private final SetOperations<String, T> setOperations;

    // IDEA虽然报错,但是依然可以注入成功, 实例化操作对象后就可以直接调用方法操作Redis数据库
    @Autowired
    public RedisHelperImpl(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void hashPut(String key, HK hashKey, T domain) {
        hashOperations.put(key, hashKey, domain);
    }

    @Override
    public Map<HK, T> hashFindAll(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public T hashGet(String key, HK hashKey) {
        return hashOperations.get(key, hashKey);
    }

    @Override
    public void hashRemove(String key, HK hashKey) {
        hashOperations.delete(key, hashKey);
    }

    @Override
    public Long listPush(String key, T domain) {
        return listOperations.rightPush(key, domain);
    }

    @Override
    public Long listUnshift(String key, T domain) {
        return listOperations.leftPush(key, domain);
    }

    @Override
    public List<T> listFindAll(String key) {
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return listOperations.range(key, 0, listOperations.size(key));
    }

    @Override
    public T listLPop(String key) {
        return listOperations.leftPop(key);
    }

    @Override
    public void valuePut(String key, T domain) {
        valueOperations.set(key, domain);
    }

    @Override
    public void valuePut(String key, T domain,Integer expireDate) {
        valueOperations.set(key, domain,expireDate,TimeUnit.MINUTES);
    }

    @Override
    public T getValue(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean expirse(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 根据模糊的key或者根据具体的key，删除redis中的数据
     *
     * @param key:
     * @author wangjunming
     * @since 2020/11/27 22:15
     */
    @Override
    public boolean deleteByKey(String key) {
        if (StringUtils.isNotBlank(key)) {
            try {
                Set<String> keys = redisTemplate.keys(key);
                log.info("redis中---查询出来的所匹配的key是：{}", keys);
                if (!CollectionUtils.isEmpty(keys)) {
                    return redisTemplate.delete(keys) > 0;
                }
                log.warn("redis中---并没有查询出key：{}，其对应的值", key);
                return Boolean.TRUE;
            } catch (Exception e) {
                log.error("redis根据key：{}，删除失败，原因是：{}", key, e);
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }

}
