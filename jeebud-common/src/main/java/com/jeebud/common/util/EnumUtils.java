package com.jeebud.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public class EnumUtils {
    private static Map<Class,Object> map = new ConcurrentHashMap<>();

    /**
     * 根据条件获取枚举对象
     * @param className 枚举类
     * @param predicate 筛选条件
     * @return
     */
    public static <T> Optional<T> getEnumObject(Class<T> className, Predicate<T> predicate) {
        if(!className.isEnum()){
            log.info("非枚举类");
            return null;
        }
        Object obj = map.get(className);
        T[] ts = null;
        if(obj == null){
            ts = className.getEnumConstants();
            map.put(className,ts);
        }else{
            ts = (T[])obj;
        }
        return Arrays.stream(ts).filter(predicate).findAny();
    }
}
