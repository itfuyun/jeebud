package com.jeebud.module.upms.service.impl;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.module.upms.model.entity.Param;
import com.jeebud.module.upms.repository.ParamRepository;
import com.jeebud.module.upms.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class ParamServiceImpl implements ParamService {

    @Autowired
    ParamRepository paramRepository;

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    @Override
    public Page<Param> page(PageQuery query) {
        return paramRepository.search(query);
    }

    /**
     * 插入
     *
     * @param param
     */
    @Override
    public Long insert(Param param) {
        Param checkParam = paramRepository.findByParamKey(param.getParamKey());
        if (!ObjectUtils.isNull(checkParam)) {
            throw new JeebudException("参数键已存在");
        }
        param.setId(null);
        param.setType(0);
        paramRepository.save(param);
        return param.getId();
    }

    /**
     * 修改
     *
     * @param param
     */
    @Override
    public void update(Param param) {
        if (ObjectUtils.isNull(param.getId())) {
            throw new JeebudException("ID不能为空");
        }
        Param checkParam = paramRepository.findById(param.getId()).orElseThrow(() -> new JeebudException("参数数据不存在"));
        if (checkParam.getType() == 1) {
            if (!checkParam.getParamGroup().equals(param.getParamGroup()) || !checkParam.getParamKey().equals(param.getParamKey())) {
                throw new JeebudException("系统内置参数不可修改组名或参数键");
            }
        }
        checkParam = paramRepository.findByParamKey(param.getParamKey());
        if (!ObjectUtils.isNull(checkParam) && !checkParam.getId().equals(param.getId())) {
            throw new JeebudException("参数键已存在");
        }
        paramRepository.save(param);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        Param checkParam = paramRepository.findById(id).orElseThrow(() -> new JeebudException("参数数据不存在"));
        if (checkParam.getType() == 1) {
            throw new JeebudException("系统内置参数不可删除");
        }
        paramRepository.deleteById(id);
    }

    @Override
    public Param findByParamKey(String paramKey) {
        return paramRepository.findByParamKey(paramKey);
    }

    @Override
    public List<Param> findByGroup(String paramGroup) {
        return paramRepository.findByParamGroup(paramGroup);
    }
}
