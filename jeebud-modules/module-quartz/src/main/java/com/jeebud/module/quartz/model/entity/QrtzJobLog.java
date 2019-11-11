package com.jeebud.module.quartz.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeebud.core.data.jpa.domain.AbstractDO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "qrtz_job_log")
public class QrtzJobLog extends AbstractDO {

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

    /** 日志信息 */
    @Column(name = "job_message")
    private String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Column(name = "status")
    private String status;

    /** 异常信息 */
    @Column(name = "exception_info")
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time",updatable = false)
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time",insertable = false)
    private Date updateTime;

}
