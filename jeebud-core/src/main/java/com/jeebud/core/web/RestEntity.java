package com.jeebud.core.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeebud.common.constant.ErrCodeEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public class RestEntity<T> implements Serializable {

    private static final long serialVersionUID = -3676525160280941163L;
    /**
     * 错误码
     */
    private int code;
    /**
     * 提示消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;
    /**
     * http状态码
     */
    @JsonIgnore
    private int httpStatus;

    public RestEntity() {
        super();
    }

    /**
     * 成功响应
     *
     * @return
     */
    public static RestEntity ok() {
        return new RestEntity().code(ErrCodeEnum.SUCCESS.getCode()).message(ErrCodeEnum.SUCCESS.getMsg());
    }

    /**
     * 失败响应
     *
     * @return
     */
    public static RestEntity failed() {
        return new RestEntity().code(ErrCodeEnum.ERROR.getCode()).message(ErrCodeEnum.ERROR.getMsg());
    }

    public RestEntity code(int code){
        this.code = code;
        return this;
    }

    public RestEntity message(String message){
        this.message = message;
        return this;
    }

    public RestEntity data(T data){
        this.data = data;
        return this;
    }

    public RestEntity httpStatus(int httpStatus){
        this.httpStatus = httpStatus;
        return this;
    }
}
