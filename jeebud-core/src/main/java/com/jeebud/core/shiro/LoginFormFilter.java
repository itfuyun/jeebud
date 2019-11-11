package com.jeebud.core.shiro;

import com.jeebud.common.constant.ErrCodeEnum;
import com.jeebud.common.util.JsonUtils;
import com.jeebud.common.util.RequestUtils;
import com.jeebud.core.web.RestEntity;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class LoginFormFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if (RequestUtils.isAjax(httpServletRequest)){
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.append(JsonUtils.toJsonString(RestEntity.ok().code(ErrCodeEnum.UNAUTHORIZED.getCode()).message(ErrCodeEnum.UNAUTHORIZED.getMsg())));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }
}
