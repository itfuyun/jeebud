package com.jeebud.module.cms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.cms.model.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface ArticleRepository extends BaseRepository<Article, Long> {
}
