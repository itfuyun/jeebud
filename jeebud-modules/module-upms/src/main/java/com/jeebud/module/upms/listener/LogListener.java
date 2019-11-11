package com.jeebud.module.upms.listener;

import com.jeebud.common.util.CglibBeanUtils;
import com.jeebud.core.log.bean.LoginLogBean;
import com.jeebud.core.log.bean.OperationLogBean;
import com.jeebud.core.log.event.LoginLogEvent;
import com.jeebud.core.log.event.OperationLogEvent;
import com.jeebud.module.upms.model.entity.LoginLog;
import com.jeebud.module.upms.model.entity.OperationLog;
import com.jeebud.module.upms.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Component
public class LogListener{
    @Autowired
    LogService logService;

    @EventListener
    public void onApplicationEvent(OperationLogEvent operationLogEvent) {
        OperationLogBean operationLogBean = (OperationLogBean) operationLogEvent.getSource();
        OperationLog operationLog = new OperationLog();
        CglibBeanUtils.copyProperties(operationLogBean,operationLog);
        logService.insertOperationLog(operationLog);
    }

    @EventListener
    public void onApplicationEvent(LoginLogEvent loginLogEvent) {
        LoginLogBean loginLogBean = (LoginLogBean) loginLogEvent.getSource();
        LoginLog loginLog = new LoginLog();
        CglibBeanUtils.copyProperties(loginLogBean,loginLog);
        logService.insertLoginLog(loginLog);
    }
}
