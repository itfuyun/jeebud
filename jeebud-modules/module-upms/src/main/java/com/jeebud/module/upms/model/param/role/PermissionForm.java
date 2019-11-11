package com.jeebud.module.upms.model.param.role;

import com.jeebud.core.data.jpa.domain.Form;
import lombok.Data;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class PermissionForm extends Form{
    private Long id;
    private List<Long> permissionIdList;
}
