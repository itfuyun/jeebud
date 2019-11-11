package com.jeebud.module.upms.model.entity;

import com.jeebud.core.data.jpa.domain.BaseDO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "sys_role")
public class Role extends BaseDO {

    /**
     * 角色名
     */
    @Column(name = "role_name")
    @NotEmpty(message = "角色名不能为空")
    private String roleName;
    /**
     * 备注
     */
    @Column
    private String remarks;
}
