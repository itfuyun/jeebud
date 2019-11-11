package com.jeebud.core.shiro.util;

import com.jeebud.core.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class ShiroUtils {
    /**
     * 获取当前登录用户
     * @return
     */
    public static ShiroUser getCurrentUser(){
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static Long getUserId(){
        return getCurrentUser().getId();
    }

    /**
     * 获取当前用户账号
     *
     * @return
     */
    public static String getUsername(){
        return getCurrentUser().getUsername();
    }
}
