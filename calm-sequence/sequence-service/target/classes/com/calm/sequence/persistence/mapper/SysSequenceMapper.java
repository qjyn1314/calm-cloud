package com.calm.sequence.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.calm.sequence.api.entity.SysSequence;
import com.calm.sequence.api.vo.SysSequenceVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysSequenceMapper extends BaseMapper<SysSequence> {


    /**
     * 根据code获取序列信息
     *
     * @param sequenceType 序列号类型
     * @author wangjunming
     * @since 2021/4/10 19:33
     * @return com.calm.sequence.api.vo.SysSequenceVo
     */
    SysSequenceVo selectSequenceByType(@Param("sequenceType") String sequenceType);

    /**
     * 根据序列号类型增加序列号的
     *
     * @param sequenceType 序列号类型
     * @param sequence 序列号
     * @author wangjunming
     * @since 2021/4/10 19:42
     */
    void updateSequenceByType(@Param("sequenceType") String sequenceType, @Param("sequence") Long sequence);

}