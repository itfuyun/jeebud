package com.jeebud.module.upms.service.impl;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.upms.model.entity.LoginLog;
import com.jeebud.module.upms.model.entity.OperationLog;
import com.jeebud.module.upms.repository.LoginLogRepository;
import com.jeebud.module.upms.repository.OperationLogRepository;
import com.jeebud.module.upms.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    OperationLogRepository operationLogRepository;
    @Autowired
    LoginLogRepository loginLogRepository;

    @Override
    public Page<OperationLog> operationLogPage(PageQuery query) {
        return operationLogRepository.search(query);
    }

    @Override
    public Page<LoginLog> loginLogPage(PageQuery query) {
        return loginLogRepository.search(query);
    }

    @Override
    public Long insertOperationLog(OperationLog log) {
        operationLogRepository.save(log);
        return log.getId();
    }

    @Override
    public Long insertLoginLog(LoginLog log) {
        loginLogRepository.save(log);
        return log.getId();
    }

    @Override
    public void clearOperationLog() {
        operationLogRepository.deleteAllInBatch();
    }

    @Override
    public void clearLoginLog() {
        loginLogRepository.deleteAllInBatch();
    }
}
