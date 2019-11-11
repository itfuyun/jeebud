package com.jeebud.module.upms.model.param.role;

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
public class RolePageQuery extends PageQuery {
    /**
     * 角色名
     */
    @QueryField(name = "roleName", type = QueryType.LIKE)
    private String roleName;
}
