package com.jeebud.module.quartz.util;

import com.jeebud.module.quartz.model.entity.QrtzJob;
import org.quartz.JobExecutionContext;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, QrtzJob qrtzJob) throws Exception {
        JobInvokeUtil.invokeMethod(qrtzJob);
    }
}
