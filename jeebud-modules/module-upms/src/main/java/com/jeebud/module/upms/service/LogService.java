package com.jeebud.module.upms.service;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.upms.model.entity.LoginLog;
import com.jeebud.module.upms.model.entity.OperationLog;
import org.springframework.data.domain.Page;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface LogService {


    /**
     * 获取操作日志列表
     *
     * @param query
     * @return
     */
    Page<OperationLog> operationLogPage(PageQuery query);

    /**
     * 获取登录日志列表
     *
     * @param query
     * @return
     */
    Page<LoginLog> loginLogPage(PageQuery query);

    /**
     * 插入操作日志
     *
     * @param log
     * @return
     */
    Long insertOperationLog(OperationLog log);

    /**
     * 插入登录日志
     *
     * @param log
     * @return
     */
    Long insertLoginLog(LoginLog log);

    /**
     * 清空操作日志
     */
    void clearOperationLog();

    /**
     * 清空登录日志
     */
    void clearLoginLog();
}
