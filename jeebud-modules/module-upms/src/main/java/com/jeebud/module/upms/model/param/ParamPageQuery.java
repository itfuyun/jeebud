package com.jeebud.module.upms.model.param;

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
public class ParamPageQuery extends PageQuery {
    /**
     * 参数名称
     */
    @QueryField(name = "paramName", type = QueryType.LIKE)
    private String paramName;
    /**
     * 参数键
     */
    @QueryField(name = "paramKey", type = QueryType.LIKE)
    private String paramKey;
    /**
     * 参数值
     */
    @QueryField(name = "paramValue", type = QueryType.LIKE)
    private String paramValue;
    /**
     * 参数组
     */
    @QueryField(name = "paramGroup", type = QueryType.LIKE)
    private String paramGroup;
    /**
     * 类型
     */
    @QueryField(name = "type", type = QueryType.EQUAL)
    private Integer type;
}
