package com.jeebud.module.quartz.service.impl;

import com.jeebud.module.quartz.model.entity.QrtzJobLog;
import com.jeebud.module.quartz.model.param.QrtzJobLogPageQuery;
import com.jeebud.module.quartz.repository.QrtzJobLogRepository;
import com.jeebud.module.quartz.service.QrtzJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class QrtzJobLogServiceImpl implements QrtzJobLogService {

    @Autowired
    QrtzJobLogRepository jobLogRepository;

    @Override
    public void create(QrtzJobLog qrtzJobLog){
        jobLogRepository.save(qrtzJobLog);
    }

    @Override
    public Page<QrtzJobLog> page(QrtzJobLogPageQuery query){
        return jobLogRepository.search(query);
    }

    @Override
    public QrtzJobLog detail(Long id){
        return jobLogRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id){
        jobLogRepository.deleteById(id);
    }

    @Override
    public void clear(){
        jobLogRepository.deleteAllInBatch();
    }

}
