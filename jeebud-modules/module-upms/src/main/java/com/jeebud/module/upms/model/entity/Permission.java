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
@Table(name = "sys_permission")
public class Permission extends BaseDO {
    /**
     * 父级ID
     */
    @Column
    @NotNull(message = "上级权限不能为空")
    private Long pid;

    /**
     * 权限名
     */
    @Column
    @NotEmpty(message = "权限名不能为空")
    private String name;

    /**
     * 权限编码
     */
    @Column
    private String code;

    /**
     * 链接
     */
    @Column
    private String url;

    /**
     * 类型 0菜单 1 按钮
     */
    @Column
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 图标
     */
    @Column
    private String icon;

    /**
     * 排序
     */
    @Column(name = "sort_num")
    @NotNull(message = "排序不能为空")
    private Integer sortNum;
}
