package com.calm.user.api.exception;

/**
 * <p>
 * explain: 用户已停用异常
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 18:03
 */
public class HaveBeenforzenException extends RuntimeException {

    public HaveBeenforzenException() {
    }

    public HaveBeenforzenException(String message) {
        super(message);
    }
}
