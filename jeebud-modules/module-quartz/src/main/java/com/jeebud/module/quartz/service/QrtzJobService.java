package com.jeebud.module.quartz.service;

import com.jeebud.module.quartz.model.entity.QrtzJob;
import com.jeebud.module.quartz.exception.TaskException;
import com.jeebud.module.quartz.model.param.QrtzJobPageQuery;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface QrtzJobService {

    /**
     * 项目启动时，初始化定时器
     *
     * @throws SchedulerException
     * @throws TaskException
     */
    void init() throws SchedulerException, TaskException;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    Page<QrtzJob> page(QrtzJobPageQuery query);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param id 调度任务ID
     * @return 调度任务对象信息
     */
    QrtzJob detail(Long id);

    /**
     * 暂停任务
     *
     * @param id 调度信息
     * @throws SchedulerException
     */
    void pauseJob(Long id) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param id 调度信息
     * @throws SchedulerException
     */
    void resumeJob(Long id) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param id 调度信息
     * @throws SchedulerException
     */
    void delete(Long id) throws SchedulerException;


    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @throws SchedulerException
     */
     void updateStatus(Long id, String status) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param id 调度信息
     * @throws SchedulerException
     */
    void run(Long id) throws SchedulerException;

    /**
     * 新增任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    Long create(QrtzJob job) throws SchedulerException, TaskException;

    /**
     * 修改任务
     *
     * @param job
     * @throws SchedulerException
     * @throws TaskException
     */
    void update(QrtzJob job) throws SchedulerException, TaskException;

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */

    boolean checkCronExpressionIsValid(String cronExpression);
}
