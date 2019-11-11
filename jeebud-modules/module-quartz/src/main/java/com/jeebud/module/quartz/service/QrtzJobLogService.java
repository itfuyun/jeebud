package com.jeebud.module.quartz.service;

import com.jeebud.module.quartz.model.entity.QrtzJobLog;
import com.jeebud.module.quartz.model.param.QrtzJobLogPageQuery;
import org.springframework.data.domain.Page;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface QrtzJobLogService {

    /**
     * 创建
     *
     * @param qrtzJobLog
     */
    void create(QrtzJobLog qrtzJobLog);

    /**
     * 分页
     *
     * @param query
     * @return
     */
    Page<QrtzJobLog> page(QrtzJobLogPageQuery query);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    QrtzJobLog detail(Long id);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 清空
     */
    void clear();

}
