package com.jeebud.common.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class CglibBeanUtils {
    /**
     * 缓存
     */
    private static final ConcurrentHashMap<String, BeanCopier> MAP_CACHES = new ConcurrentHashMap<>();

    /**
     * 拷贝
     *
     * @param source
     * @param target
     * @param <O>
     * @param <T>
     * @return
     */
    public static <O, T> T copyProperties(O source, T target) {
        return copy(source, target);
    }

    /**
     * 拷贝 支持函数式接口编程
     *
     * @param source
     * @param target
     * @param action
     * @param <O>
     * @param <T>
     * @return
     */
    public static <O, T> T copyProperties(O source, T target, IAction<T> action) {
        target = copy(source, target);
        action.run(target);
        return target;
    }

    /**
     * 拷贝
     *
     * @param source
     * @param target
     * @param <O>
     * @param <T>
     * @return
     */
    private static <O, T> T copy(O source, T target) {
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier;
        if (!MAP_CACHES.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            MAP_CACHES.put(baseKey, copier);
        } else {
            copier = MAP_CACHES.get(baseKey);
        }
        copier.copy(source, target, null);
        return target;
    }

    /**
     * 生成缓存map key值
     *
     * @param c1
     * @param c2
     * @return
     */
    private static String generateKey(Class<?> c1, Class<?> c2) {
        return c1.toString() + c2.toString();
    }

    @FunctionalInterface
    private interface IAction<T> {
        /**
         * 执行
         * @param param
         */
        void run(T param);
    }

}
