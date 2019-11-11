package com.jeebud.core.log.bean;

import lombok.Data;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class LoginLogBean {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录时间
     */
    private Date loginTime;
}
