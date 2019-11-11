package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: 消息类型</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum  MsgTypeEnum {
    /**
     * 文本
     */
    TEXT("TEXT"),
    /**
     * 图片
     */
    IMAGE("IMAGE");

    MsgTypeEnum(String type) {
        this.type = type;
    }

    private String type;
}
