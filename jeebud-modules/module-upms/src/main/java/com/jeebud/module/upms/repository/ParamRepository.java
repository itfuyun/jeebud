package com.jeebud.module.upms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.upms.model.entity.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface ParamRepository extends BaseRepository<Param, Long> {
    /**
     * 根据key查询
     *
     * @param paramKey
     * @return
     */
    Param findByParamKey(String paramKey);

    /**
     * 根据分组查询
     *
     * @param paramGroup
     * @return
     */
    List<Param> findByParamGroup(String paramGroup);
}
