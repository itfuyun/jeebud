package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum CaptchaTypeEnum {
    /**
     * 数据流形式
     */
    BYTE_IMAGE("BYTE_IMAGE", "数据流形式"),
    /**
     * BASE64形式
     */
    BASE64_IMAGE("BASE64_IMAGE","BASE64形式");

    CaptchaTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    private String type;
    private String msg;

}
