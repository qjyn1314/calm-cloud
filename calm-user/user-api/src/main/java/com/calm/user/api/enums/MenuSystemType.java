package com.calm.user.api.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * explain: 系统分类：1-后台管理系统；2-前台管理系统
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 17:53
 */
public enum MenuSystemType {

    /**后台管理系统*/
    BACKSTAGE(1,"后台管理系统"),
    /**前台管理系统*/
    RECEPTION(2,"前台管理系统"),

        ;
    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String desc;

    MenuSystemType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
