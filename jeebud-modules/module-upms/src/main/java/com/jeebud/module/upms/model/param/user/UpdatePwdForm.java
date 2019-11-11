package com.jeebud.module.upms.model.param.user;

import com.jeebud.core.data.jpa.domain.Form;
import lombok.Data;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class UpdatePwdForm extends Form{
    private Long id;
    private String oldPwd;
    private String newPwd;
}
