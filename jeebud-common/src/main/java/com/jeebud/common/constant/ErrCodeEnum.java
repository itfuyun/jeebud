package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum ErrCodeEnum {
    /**
     * 系统内部错误
     */
    ERROR(ErrCodeEnum.ERROR_CODE, "系统内部错误"),
    /**
     * 成功
     */
    SUCCESS(ErrCodeEnum.SUCCESS_CODE,"操作成功"),
    /**
     * 业务类错误
     */
    BIZ_ERROR(ErrCodeEnum.BIZ_ERROR_CODE, "业务类错误"),
    /**
     * 请求参数错误
     */
    INVALID_PARAM(ErrCodeEnum.INVALID_PARAM_CODE,"请求参数错误"),
    /**
     * 请求接口不存在
     */
    NOT_FOUND(ErrCodeEnum.NOT_FOUND_CODE,"请求接口不存在"),
    /**
     * 未登陆
     */
    UNAUTHORIZED(ErrCodeEnum.UNAUTHORIZED_CODE,"未登陆"),
    /**
     * 无权限
     */
    FORBIDDEN(ErrCodeEnum.FORBIDDEN_CODE,"无权限");

    ErrCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public static final int ERROR_CODE = -1;
    public static final int SUCCESS_CODE = 0;
    public static final int BIZ_ERROR_CODE = 10000;
    public static final int INVALID_PARAM_CODE = 10400;
    public static final int UNAUTHORIZED_CODE = 10401;
    public static final int FORBIDDEN_CODE = 10403;
    public static final int NOT_FOUND_CODE = 10404;
}
