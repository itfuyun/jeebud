package com.jeebud.module.cms.model.param;

import com.jeebud.core.data.annotation.QueryField;
import com.jeebud.core.data.annotation.QueryType;
import com.jeebud.core.data.jpa.domain.PageQuery;
import lombok.Data;


/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class ColumnPageQuery extends PageQuery {
    /**
     * 栏目名称
     */
    @QueryField(name = "name", type = QueryType.LIKE)
    private String name;
}
