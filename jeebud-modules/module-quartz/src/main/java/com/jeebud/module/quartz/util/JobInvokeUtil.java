package com.jeebud.module.quartz.util;


import com.jeebud.common.util.SpringUtils;
import com.jeebud.common.util.StringUtils;
import com.jeebud.module.quartz.model.entity.QrtzJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class JobInvokeUtil {
    /**
     * 执行方法
     *
     * @param qrtzJob 系统任务
     */
    public static void invokeMethod(QrtzJob qrtzJob) throws Exception {
        Object bean = SpringUtils.getBean(qrtzJob.getJobName());
        String methodName = qrtzJob.getMethodName();
        String methodParams = qrtzJob.getMethodParams();
        invokeSpringBean(bean, methodName, methodParams);
    }

    /**
     * 调用任务方法
     *
     * @param bean         目标对象
     * @param methodName   方法名称
     * @param methodParams 方法参数
     */
    private static void invokeSpringBean(Object bean, String methodName, String methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (StringUtils.isNotEmpty(methodParams)) {
            Method method = bean.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(bean, methodParams);
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }
}
