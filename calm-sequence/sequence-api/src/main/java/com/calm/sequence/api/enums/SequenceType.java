package com.calm.sequence.api.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * explain: 序列号类型
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:53
 */
public enum SequenceType {

    /**用户表编码*/
    USER("USER","用户表编码"),

        ;
    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String desc;

    SequenceType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
