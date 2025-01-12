package com.calm.user.api.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * explain: 菜单状态：1-启用；0-停用
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:53
 */
public enum MenuStatus {

    /**启用*/
    ENABLE(1,"启用"),
    /**停用*/
    DEACTIVATED(0,"停用"),

        ;
    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String desc;

    MenuStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
