package com.jeebud.core.log;

import com.jeebud.common.util.IpUtils;
import com.jeebud.common.util.JsonUtils;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.core.log.bean.LoginLogBean;
import com.jeebud.core.log.bean.OperationLogBean;
import com.jeebud.core.log.event.LoginLogEvent;
import com.jeebud.core.log.event.OperationLogEvent;
import com.jeebud.core.shiro.ShiroUser;
import com.jeebud.core.shiro.util.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Component
public class LogPublisher {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 操作日志消息
     *
     * @param joinPoint
     * @param request
     * @param username
     */
    @Async
    public void publishOperationLog(JoinPoint joinPoint, HttpServletRequest request, String username){
        // 获取方法的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Operation operation = method.getAnnotation(Operation.class);
        if(!ObjectUtils.isNull(operation)){
            //记录日志
            OperationLogBean operationLog = new OperationLogBean();
            operationLog.setIp(IpUtils.getIpAddr(request));
            operationLog.setModule(operation.module());
            operationLog.setInfo(operation.info());
            operationLog.setOperator(username);
            operationLog.setCreateTime(new Date());
            operationLog.setOpType(operation.opType().name());
            operationLog.setReqMethod(request.getMethod());
            operationLog.setReqParam(JsonUtils.toJsonString(request.getParameterMap()));
            applicationContext.publishEvent(new OperationLogEvent(operationLog));
        }
    }

    /**
     * 登陆日志消息
     *
     * @param shiroUser
     * @param ip
     */
    @Async
    public void publishLoginLog(ShiroUser shiroUser, String ip){
        //记录日志
        LoginLogBean loginLog = new LoginLogBean();
        loginLog.setIp(ip);
        ShiroUser user = ShiroUtils.getCurrentUser();
        loginLog.setLoginTime(new Date());
        loginLog.setUserId(user.getId());
        loginLog.setUsername(user.getUsername());
        applicationContext.publishEvent(new LoginLogEvent(loginLog));
    };

}
