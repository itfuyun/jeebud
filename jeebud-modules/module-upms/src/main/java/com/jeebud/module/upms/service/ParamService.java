package com.jeebud.module.upms.service;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.upms.model.entity.Param;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface ParamService {

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    Page<Param> page(PageQuery query);

    /**
    * 插入
    *
    * @param param
    */
    Long insert(Param param);

    /**
     * 修改
     *
     * @param param
     */
    void update(Param param);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据键名查询
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
    List<Param> findByGroup(String paramGroup);
}
