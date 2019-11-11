package com.jeebud.module.upms.model.entity;

import com.jeebud.core.data.jpa.domain.BaseDO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseDO {

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    @NotNull(message = "角色不能为空")
    private Long roleId;

    /**
     * 名字
     */
    @Column
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 用户名
     */
    @Column
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 头像
     */
    @Column
    private String avatar;

    /**
     * 邮箱
     */
    @Column
    private String email;

    /**
     * 手机
     */
    @Column
    private String mobile;

    /**
     * 介绍
     */
    @Column
    private String profile;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 状态 0正常 1锁定
     */
    @Column
    private Integer status;

    /**
     * 超级管理员标记 1是 0否
     */
    @Column(name = "admin_flag",updatable = false)
    private Integer adminFlag;
}
