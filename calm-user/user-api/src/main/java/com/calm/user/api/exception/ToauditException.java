package com.calm.user.api.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * <p>
 * explain: 用户待审核异常
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 18:01
 */
public class ToauditException extends AuthenticationServiceException {

    public ToauditException(String message) {
        super(message);
    }
}
