package com.calm.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/30 21:30
 */
@Data
public class User implements Serializable {

    private Long userId;

    private String username;

    public User() {
    }

    public User(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
