package com.jeebud.module.cms.service;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.cms.model.entity.Article;
import org.springframework.data.domain.Page;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface ArticleService {

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    Page<Article> page(PageQuery query);

    /**
    * 插入
    *
    * @param article
    * @return
    */
    Long insert(Article article);

    /**
     * 修改
     *
     * @param article
     */
    void update(Article article);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Article findById(Long id);
}
