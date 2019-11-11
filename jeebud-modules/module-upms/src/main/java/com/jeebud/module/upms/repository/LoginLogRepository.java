package com.jeebud.module.upms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.upms.model.entity.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface LoginLogRepository extends BaseRepository<LoginLog,Long> {
}
