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
public class OperationLogBean {
    /**
     * 模块
     */
    private String module;

    /**
     * 描述
     */
    private String info;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 请求方式
     */
    private String reqMethod;

    /**
     * 操作类型
     */
    private String opType;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;
}
