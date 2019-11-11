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
@Table(name = "sys_operation_log")
public class OperationLog extends AbstractDO {

    /**
     * 模块
     */
    @Column(name = "module")
    private String module;

    /**
     * 描述
     */
    @Column(name = "info")
    private String info;

    /**
     * 请求参数
     */
    @Column(name = "req_param")
    private String reqParam;

    /**
     * 请求方式
     */
    @Column(name = "req_method")
    private String reqMethod;

    /**
     * 操作类型
     */
    @Column(name = "op_type")
    private String opType;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 操作IP
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time",updatable = false)
    private Date createTime;

}
