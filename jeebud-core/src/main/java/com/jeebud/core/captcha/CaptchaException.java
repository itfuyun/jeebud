package com.jeebud.core.captcha;

import org.apache.shiro.authc.CredentialsException;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class CaptchaException extends CredentialsException {

    public CaptchaException() {
        super();
    }


    public CaptchaException(String message) {
        super(message);
    }


    public CaptchaException(Throwable cause) {
        super(cause);
    }


    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
