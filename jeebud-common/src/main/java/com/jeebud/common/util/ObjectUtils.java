package com.jeebud.common.util;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class ObjectUtils {

    /**
     * 判断空集合
     *
     * @param list
     * @return
     */
    public static boolean isNullList(List list) {
        return isNull(list) || list.size() == 0;
    }

    /**
     * 判断空对象
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断空对象组
     *
     * @param objs
     * @return
     */
    public static boolean isNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null || "".equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断非空对象
     *
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

}
