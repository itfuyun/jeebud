package com.jeebud.module.upms.model.param.log;

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
public class OperationLogPageQuery extends PageQuery {
    /**
     * 模块
     */
    @QueryField(name = "module", type = QueryType.LIKE)
    private String module;

    /**
     * 操作人
     */
    @QueryField(name = "operator", type = QueryType.LIKE)
    private String operator;
}
