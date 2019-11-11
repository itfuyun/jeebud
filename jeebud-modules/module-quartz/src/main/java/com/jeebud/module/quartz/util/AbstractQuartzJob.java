package com.jeebud.module.quartz.util;

import com.jeebud.common.util.CglibBeanUtils;
import com.jeebud.common.util.ExceptionUtils;
import com.jeebud.common.util.SpringUtils;
import com.jeebud.module.quartz.constant.ScheduleConsts;
import com.jeebud.module.quartz.model.entity.QrtzJob;
import com.jeebud.module.quartz.model.entity.QrtzJobLog;
import com.jeebud.module.quartz.service.QrtzJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        QrtzJob sysJob = new QrtzJob();
        CglibBeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConsts.TASK_PROPERTIES), sysJob);
        try {
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param qrtzJob 系统计划任务
     */
    protected void before(JobExecutionContext context, QrtzJob qrtzJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void after(JobExecutionContext context, QrtzJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final QrtzJobLog jobLog = new QrtzJobLog();
        jobLog.setJobName(sysJob.getJobName());
        jobLog.setJobGroup(sysJob.getJobGroup());
        jobLog.setMethodName(sysJob.getMethodName());
        jobLog.setMethodParams(sysJob.getMethodParams());
        jobLog.setCreateTime(new Date());
        long runMs = System.currentTimeMillis() - startTime.getTime();
        jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLog.setStatus("1");
            String errorMsg = ExceptionUtils.getExceptionMessage(e);
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus("0");
        }

        // 写入数据库当中
        SpringUtils.getBean(QrtzJobLogService.class).create(jobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param qrtzJob 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, QrtzJob qrtzJob) throws Exception;
}
