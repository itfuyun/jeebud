package com.jeebud.module.quartz.model.entity;

import com.jeebud.core.data.jpa.domain.BaseDO;
import com.jeebud.module.quartz.constant.ScheduleConsts;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "qrtz_job")
public class QrtzJob extends BaseDO {

    /** 任务名称 */
    @Column(name = "job_name")
    private String jobName;

    /** 任务组名 */
    @Column(name = "job_group")
    private String jobGroup;

    /** 任务方法 */
    @Column(name = "method_name")
    private String methodName;

    /** 方法参数 */
    @Column(name = "method_params")
    private String methodParams;

    /** cron执行表达式 */
    @Column(name = "cron_expression")
    private String cronExpression;

    /** cron计划策略 */
    @Column(name = "misfire_policy")
    private String misfirePolicy = ScheduleConsts.MISFIRE_DEFAULT;

    /** 是否并发执行（1允许 0禁止） */
    @Column(name = "concurrent")
    private String concurrent;

    /** 任务状态（1正常 0暂停） */
    @Column(name = "status")
    private String status;

    /** 备注 */
    @Column(name = "remarks")
    private String remarks;
}
