package com.calm.sequence.api.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysSequence {
    private static final long serialVersionUID = 1L;
    /**
     * 序列号
     */
    private Long id;

    /**
     * 序列号type
     */
    private String sequenceType;

    /**
     * 序列号
     */
    private Long sequence;

    /**
     * 创建时间
     */
    private Date createTime;
}