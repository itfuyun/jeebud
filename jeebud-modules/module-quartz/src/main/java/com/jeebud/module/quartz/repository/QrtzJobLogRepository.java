package com.jeebud.module.quartz.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.quartz.model.entity.QrtzJobLog;
import org.springframework.stereotype.Repository;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface QrtzJobLogRepository extends BaseRepository<QrtzJobLog, Long> {
}
