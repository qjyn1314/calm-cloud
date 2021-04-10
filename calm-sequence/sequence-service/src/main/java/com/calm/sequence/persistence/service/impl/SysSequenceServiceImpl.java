package com.calm.sequence.persistence.service.impl;

import cn.hutool.core.date.DateUtil;
import com.calm.sequence.api.entity.SysSequence;
import com.calm.sequence.api.utils.SequenceUtil;
import com.calm.sequence.api.vo.SysSequenceVo;
import com.calm.sequence.persistence.mapper.SysSequenceMapper;
import com.calm.sequence.persistence.service.SysSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 13:59
 */
@Service
public class SysSequenceServiceImpl implements SysSequenceService {

    @Autowired
    private SysSequenceMapper sysSequenceMapper;

    /**
     * 获取对应类型的序列号
     *
     * @param sequenceType 类型
     * @param length       长度  可为空，为空则是
     * @return java.lang.String
     * @author wangjunming
     * @since 2021/4/10 14:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getSequenceNum(String sequenceType, Integer length) {
        SysSequenceVo sequenceVo = sysSequenceMapper.selectSequenceByType(sequenceType);
        if (null == sequenceVo) {
            SysSequence sequence = new SysSequence();
            sequence.setSequenceType(sequenceType);
            sequence.setSequence(1L);
            sequence.setCreateTime(DateUtil.date());
            sysSequenceMapper.insert(sequence);
            return SequenceUtil.getSequenceCodeByDate(sequence.getSequence(),length);
        }
        Long sequence = sequenceVo.getSequence();
        ++sequence;
        sysSequenceMapper.updateSequenceByType(sequenceType,sequence);
        return SequenceUtil.getSequenceCodeByDate(sequence,length);
    }
}
