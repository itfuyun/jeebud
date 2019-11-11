package com.jeebud.core.captcha;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeebud.common.constant.CaptchaTypeEnum;
import lombok.Data;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class Captcha {
    /**
     * 验证码key
     */
    private String captchaKey;
    /**
     * 验证码值
     */
    @JsonIgnore
    private String value;
    /**
     * 验证码图片
     */
    private String image;
    /**
     * 验证码流
     */
    private byte[] bytes;
    /**
     * 验证码类型
     */
    private CaptchaTypeEnum captchaType;

}
