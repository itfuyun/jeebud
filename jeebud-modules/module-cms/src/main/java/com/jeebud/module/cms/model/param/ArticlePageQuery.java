package com.jeebud.module.cms.model.param;

import com.jeebud.core.data.annotation.QueryField;
import com.jeebud.core.data.annotation.QueryType;
import com.jeebud.core.data.jpa.domain.PageQuery;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class ArticlePageQuery extends PageQuery {
    /**
     * 标题
     */
    @QueryField(name = "title", type = QueryType.LIKE)
    private String title;
    /**
     * 栏目ID
     */
    @QueryField(name = "columnId", type = QueryType.EQUAL)
    private Integer columnId;
}
