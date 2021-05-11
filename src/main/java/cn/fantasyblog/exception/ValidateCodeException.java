package cn.fantasyblog.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description 验证码异常
 * @Author Cy
 * @Date 2021-03-24 21:20
 */

public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
