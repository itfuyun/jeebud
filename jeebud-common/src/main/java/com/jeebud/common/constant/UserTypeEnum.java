package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: 用户类型</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum UserTypeEnum {
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 注册会员
     */
    MEMBER(2);

    UserTypeEnum(Integer type) {
        this.type = type;
    }

    private Integer type;
}
