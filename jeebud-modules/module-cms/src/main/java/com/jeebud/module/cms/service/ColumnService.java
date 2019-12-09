package com.jeebud.module.cms.service;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.cms.model.entity.Column;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface ColumnService {

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    Page<Column> page(PageQuery query);

    /**
    * 插入
    *
    * @param column
    * @return
    */
    Long insert(Column column);

    /**
     * 修改
     *
     * @param column
     */
    void update(Column column);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 获取所有栏目
     *
     * @return
     */
    List<Column> listAll();
}
