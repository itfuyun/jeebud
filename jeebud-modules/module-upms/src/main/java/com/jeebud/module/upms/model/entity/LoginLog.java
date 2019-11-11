package com.jeebud.module.upms.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeebud.core.data.jpa.domain.AbstractDO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "sys_login_log")
public class LoginLog extends AbstractDO {
    /**
     * 用户名
     */
    @Column
    private String username;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 登录IP
     */
    @Column
    private String ip;

    /**
     * 登录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "login_time",updatable = false)
    private Date loginTime;
}
