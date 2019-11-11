package com.jeebud.module.quartz.service.impl;

import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.module.quartz.constant.ScheduleConsts;
import com.jeebud.module.quartz.exception.TaskException;
import com.jeebud.module.quartz.model.entity.QrtzJob;
import com.jeebud.module.quartz.model.param.QrtzJobPageQuery;
import com.jeebud.module.quartz.repository.QrtzJobRepository;
import com.jeebud.module.quartz.service.QrtzJobService;
import com.jeebud.module.quartz.util.CronUtils;
import com.jeebud.module.quartz.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class QrtzJobServiceImpl implements QrtzJobService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QrtzJobRepository jobRepository;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    @Override
    public void init() throws SchedulerException, TaskException {
        List<QrtzJob> jobList = jobRepository.findAll();
        for (QrtzJob job : jobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
    }

    @Override
    public Page<QrtzJob> page(QrtzJobPageQuery query) {
        return jobRepository.search(query);
    }

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param id 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public QrtzJob detail(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    /**
     * 暂停任务
     *
     * @param id 调度信息
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void pauseJob(Long id) throws SchedulerException {
        QrtzJob job = jobRepository.findById(id).orElse(null);
        if(ObjectUtils.isNull(job)){
            throw new JeebudException("任务调度不存在");
        }
        job.setStatus(ScheduleConsts.Status.PAUSE.getValue());
        jobRepository.save(job);
        ScheduleUtils.pauseJob(scheduler, job.getId());
    }

    /**
     * 恢复任务
     *
     * @param id 调度信息
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void resumeJob(Long id) throws SchedulerException {
        QrtzJob job = jobRepository.findById(id).orElse(null);
        if(ObjectUtils.isNull(job)){
            throw new JeebudException("任务调度不存在");
        }
        job.setStatus(ScheduleConsts.Status.NORMAL.getValue());
        jobRepository.save(job);
        ScheduleUtils.resumeJob(scheduler, id);
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param id 调度信息
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(Long id) throws SchedulerException {
        jobRepository.deleteById(id);
        ScheduleUtils.deleteScheduleJob(scheduler, id);
    }


    /**
     *
     * @param id
     * @param status
     * @throws SchedulerException
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void updateStatus(Long id, String status) throws SchedulerException {
        if (ScheduleConsts.Status.NORMAL.getValue().equals(status)) {
            resumeJob(id);
        } else if (ScheduleConsts.Status.PAUSE.getValue().equals(status)) {
            pauseJob(id);
        }
    }

    /**
     * 立即运行任务
     *
     * @param id 调度信息
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void run(Long id) throws SchedulerException {
        QrtzJob job = jobRepository.findById(id).orElse(null);
        ScheduleUtils.run(scheduler, job);
    }

    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Long create(QrtzJob job) throws SchedulerException, TaskException {
        if(!checkCronExpressionIsValid(job.getCronExpression())){
            throw new JeebudException("cron 表达式有误");
        }
        job.setStatus(ScheduleConsts.Status.PAUSE.getValue());
        jobRepository.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
        return job.getId();
    }

    /**
     * 修改任务
     *
     * @param job 调度信息 调度信息
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void update(QrtzJob job) throws SchedulerException, TaskException {
        if(!checkCronExpressionIsValid(job.getCronExpression())){
            throw new JeebudException("cron 表达式有误");
        }
        job.setStatus(ScheduleConsts.Status.PAUSE.getValue());
        jobRepository.save(job);
        ScheduleUtils.updateScheduleJob(scheduler, job);
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}
