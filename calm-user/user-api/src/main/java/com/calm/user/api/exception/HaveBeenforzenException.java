package com.calm.user.api.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * <p>
 * explain: 用户已停用异常
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/16 18:03
 */
public class HaveBeenforzenException extends AuthenticationServiceException {

    public HaveBeenforzenException(String message) {
        super(message);
    }
}
