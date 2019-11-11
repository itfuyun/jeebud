package com.jeebud.module.quartz.job;

import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Component("jeebudJob")
public class JeebudJob
{
    public void runTask(String params)
    {
        System.out.println("执行,参数：" + params);
    }

}
