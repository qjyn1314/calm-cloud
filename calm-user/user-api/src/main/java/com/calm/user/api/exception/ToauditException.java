package com.calm.user.api.exception;

/**
 * <p>
 * explain: 用户待审核异常
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 18:01
 */
public class ToauditException extends RuntimeException{

    public ToauditException() {
    }

    public ToauditException(String message) {
        super(message);
    }
}
