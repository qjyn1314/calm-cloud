package com.calm.sequence.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysSequenceDto implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

