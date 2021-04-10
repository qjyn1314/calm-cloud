package com.calm.sequence.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysSequenceDto implements Serializable {
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

    private static final long serialVersionUID = 1L;
}

