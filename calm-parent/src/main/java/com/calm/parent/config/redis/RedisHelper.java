package com.calm.parent.config.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/3/1 13:41
 */
public interface RedisHelper<HK, T> {

    String USER_KEY = "user_key_";
    /**
     * 通配符  “*” 号
     */
    String ASTERISK = "*";

    String USER_DEL_KEY = USER_KEY + ASTERISK;

    /**
     * Hash结构 添加元素 * @param key key * @param hashKey hashKey * @param domain 元素
     */
    void hashPut(String key, HK hashKey, T domain);

    /**
     * Hash结构 获取指定key所有键值对 * @param key * @return
     */
    Map<HK, T> hashFindAll(String key);

    /**
     * Hash结构 获取单个元素 * @param key * @param hashKey * @return
     */
    T hashGet(String key, HK hashKey);

    void hashRemove(String key, HK hashKey);

    /**
     * List结构 向尾部(Right)添加元素 * @param key * @param domain * @return
     */
    Long listPush(String key, T domain);

    /**
     * List结构 向头部(Left)添加元素 * @param key * @param domain * @return
     */
    Long listUnshift(String key, T domain);

    /**
     * List结构 获取所有元素 * @param key * @return
     */
    List<T> listFindAll(String key);

    /**
     * List结构 移除并获取数组第一个元素 * @param key * @return
     */
    T listLPop(String key);

    /**
     * 对象的实体类
     *
     * @param key
     * @param domain
     * @return
     */
    void valuePut(String key, T domain);

    /**
     * 获取对象实体类
     *
     * @param key
     * @return
     */
    T getValue(String key);

    /**
     * 删除
     * @param key
     */
    void remove(String key);

    /**
     * 设置过期时间
     *
     * @param key      键
     * @param timeout  时间
     * @param timeUnit 时间单位
     */
    boolean expirse(String key, long timeout, TimeUnit timeUnit);

    /**
     * 根据模糊的key或者根据具体的key，删除redis中的数据
     *
     * @param key:
     */
    boolean deleteByKey(String key);
}
