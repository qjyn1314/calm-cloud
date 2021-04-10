package com.calm.sequence.persistence.service;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 13:59
 */
public interface SysSequenceService {

    /**
     * 获取对应类型的序列号
     *
     * @param sequenceType 类型
     * @param length       长度
     * @return java.lang.String
     * @author wangjunming
     * @since 2021/4/10 14:07
     */
    String getSequenceNum(String sequenceType, Integer length);
}
