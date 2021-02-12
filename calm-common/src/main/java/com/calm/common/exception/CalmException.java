package com.calm.common.exception;

/**
 * <p>
 * explain: 公共异常类
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/28 14:51
 */
public class CalmException extends RuntimeException {

    public CalmException() {
    }

    public CalmException(String message) {
        super(message);
    }

}
