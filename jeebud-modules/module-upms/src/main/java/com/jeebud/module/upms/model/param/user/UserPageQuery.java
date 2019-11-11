package com.jeebud.module.upms.model.param.user;

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
public class UserPageQuery extends PageQuery {
    /**
     * 用户名
     */
    @QueryField(name = "username", type = QueryType.LIKE)
    private String username;

    /**
     * 姓名
     */
    @QueryField(name = "name", type = QueryType.LIKE)
    private String name;

    /**
     * 手机
     */
    @QueryField(name = "mobile", type = QueryType.LIKE)
    private String mobile;

    /**
     * 邮箱
     */
    @QueryField(name = "email", type = QueryType.LIKE)
    private String email;

    /**
     * 用户状态
     */
    @QueryField(name = "status", type = QueryType.EQUAL)
    private Integer status;
}
