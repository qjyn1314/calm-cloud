package com.calm.sequence.api.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SysSequence {
    private static final long serialVersionUID = 1L;
    /**
     * 序列号
     */
    private Long sequenceId;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}