package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum PermissionEnum {
    /**
     * 菜单
     */
    MENU(0, "菜单"),
    /**
     * 按钮
     */
    BUTTON(1,"按钮");

    PermissionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

}
