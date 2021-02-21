package com.calm.user.api.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * explain: 用户状态流转枚举类
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:53
 */
public enum UserStatus {

    /**待审核*/
    TO_AUDIT(0,"待审核"),
    /**已审核*/
    THE_APPROVED(1,"已审核"),
    /**已冻结*/
    HAVE_BEEN_FROZEN(2,"已冻结"),

        ;
    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String desc;

    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
