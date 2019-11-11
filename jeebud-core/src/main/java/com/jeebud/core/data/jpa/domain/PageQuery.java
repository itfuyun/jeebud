package com.jeebud.core.data.jpa.domain;

import lombok.Data;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class PageQuery extends Query {
    private int page = 1;
    private int limit = 20;

}
