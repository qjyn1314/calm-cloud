package com.calm.user.api.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * explain: 用户性别枚举类
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:53
 */
public enum UserSex {

    /**女*/
    FEMALE(0,"女"),
    /**男*/
    MALE(1,"男"),
    /**未知*/
    UNKNOWN(2,"未知"),

        ;
    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String desc;

    UserSex(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
