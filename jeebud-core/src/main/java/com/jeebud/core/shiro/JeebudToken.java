package com.jeebud.core.shiro;

import com.jeebud.common.constant.LoginTypeEnum;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class JeebudToken extends UsernamePasswordToken {
    /**
     * 验证码
     */
    private String captchaCode;
    /**
     * 验证码KEY
     */
    private String captchaKey;
    /**
     * 登录类型
     */
    private LoginTypeEnum loginType;

    public JeebudToken(String username, String password, String captchaKey, String captchaCode, LoginTypeEnum loginType) {
        this(username, password, captchaKey, captchaCode, loginType, false);
    }

    public JeebudToken(String username, String password, String captchaKey, String captchaCode, LoginTypeEnum loginType, boolean rememberMe) {
        super(username, password, rememberMe, null);
        this.captchaKey = captchaKey;
        this.captchaCode = captchaCode;
        this.loginType = loginType;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public LoginTypeEnum getLoginType() {
        return loginType;
    }
}
