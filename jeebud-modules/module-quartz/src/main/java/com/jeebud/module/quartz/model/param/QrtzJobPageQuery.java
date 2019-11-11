package com.jeebud.module.quartz.model.param;

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
public class QrtzJobPageQuery extends PageQuery {
    @QueryField(type = QueryType.LIKE,name = "jobName")
    private String jobName;

    @QueryField(type = QueryType.LIKE,name = "jobGroup")
    private String jobGroup;

    @QueryField(type = QueryType.LIKE,name = "methodName")
    private String methodName;

    @QueryField(type = QueryType.EQUAL,name = "status")
    private String status;
}
