package com.jeebud.common.util;

import java.util.UUID;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class IdGenUtils {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
