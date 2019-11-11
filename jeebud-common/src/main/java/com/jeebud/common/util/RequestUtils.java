package com.jeebud.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class RequestUtils {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    public static boolean isAjax(HttpServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if (XML_HTTP_REQUEST.equalsIgnoreCase(header)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
