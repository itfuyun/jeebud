package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: 登陆类型</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum LoginTypeEnum {
    /**
     * 管理员端
     */
    ADMIN_LOGIN("ADMIN", "管理员端登录"),
    MEMBER_LOGIN("MEMBER", "会员端登录");


    LoginTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    private String type;
    private String msg;

}
