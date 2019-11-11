package com.jeebud.module.upms.model.param.permission;

import com.jeebud.core.data.annotation.QueryField;
import com.jeebud.core.data.annotation.QueryType;
import com.jeebud.core.data.jpa.domain.Query;
import lombok.Data;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class PermissionQuery extends Query {

    /**
     * 权限名
     */
    @QueryField(name = "name", type = QueryType.LIKE)
    private String name;
}
