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
public class LoginLogPageQuery extends PageQuery {
    /**
     * 登录用户名
     */
    @QueryField(name = "username", type = QueryType.LIKE)
    private String username;
}
