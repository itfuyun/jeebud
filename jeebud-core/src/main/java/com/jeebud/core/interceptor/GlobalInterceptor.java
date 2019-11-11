package com.jeebud.core.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class GlobalInterceptor implements HandlerInterceptor {

    @Value("${jeebud.sys.resCtx}")
    private String resCtx;
    @Value("${jeebud.sys.serverCtx}")
    private String serverCtx;
    @Value("${jeebud.sys.version}")
    private String version;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //资源路径
        request.setAttribute("resCtx",resCtx);
        //调用服务路径
        request.setAttribute("serverCtx",serverCtx);
        //系统版本
        request.setAttribute("version",version);
        return true;
    }
}
