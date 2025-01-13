package com.calm.web.exception;

/**
 * <p>
 * explain: 公共异常类
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/28 14:51
 */
public class ClientException extends RuntimeException {

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

}
