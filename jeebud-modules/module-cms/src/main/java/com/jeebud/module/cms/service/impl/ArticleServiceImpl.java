package com.jeebud.module.cms.service.impl;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.module.cms.model.entity.Article;
import com.jeebud.module.cms.repository.ArticleRepository;
import com.jeebud.module.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    /**
     * 获取列表
     *
     * @param query
     * @return
     */
    @Override
    public Page<Article> page(PageQuery query) {
        return articleRepository.search(query);
    }

    /**
    * 插入
    *
    * @param article
    */
    @Override
    public Long insert(Article article) {
		article.setId(null);
		article.setCreateTime(new Date());
		articleRepository.save(article);
		return article.getId();
    }

    /**
     * 修改
     *
     * @param article
     */
    @Override
    public void update(Article article) {
        if (ObjectUtils.isNull(article.getId())) {
            throw new JeebudException("ID不能为空");
        }
        article.setUpdateTime(new Date());
		articleRepository.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
		articleRepository.deleteById(id);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(()->new JeebudException("文章不存在"));
    }
}
