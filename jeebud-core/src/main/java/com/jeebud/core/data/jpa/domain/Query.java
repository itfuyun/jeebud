package com.jeebud.core.data.jpa.domain;

import com.jeebud.common.util.StringUtils;
import lombok.Data;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class Query extends BaseParam {

    private String sort;
    private String direction = "DESC";

    public boolean hasSort(){
        return StringUtils.isNotEmpty(sort);
    }
}
