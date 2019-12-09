package com.jeebud.module.cms.service.impl;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.module.cms.model.entity.Column;
import com.jeebud.module.cms.repository.ColumnRepository;
import com.jeebud.module.cms.service.ColumnService;
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
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    ColumnRepository columnRepository;

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    @Override
    public Page<Column> page(PageQuery query) {
        return columnRepository.search(query);
    }

    /**
    * 插入
    *
    * @param column
    */
    @Override
    public Long insert(Column column) {
		column.setId(null);
		columnRepository.save(column);
		return column.getId();
    }

    /**
     * 修改
     *
     * @param column
     */
    @Override
    public void update(Column column) {
        if (ObjectUtils.isNull(column.getId())) {
            throw new JeebudException("ID不能为空");
        }
		columnRepository.save(column);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
		columnRepository.deleteById(id);
    }

    @Override
    public List<Column> listAll() {
        return columnRepository.findAll();
    }
}
