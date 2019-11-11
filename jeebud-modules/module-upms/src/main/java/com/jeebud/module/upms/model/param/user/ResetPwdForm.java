package com.jeebud.module.upms.model.param.user;

import com.jeebud.core.data.jpa.domain.Form;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class ResetPwdForm extends Form{
    private Long id;
    @NotEmpty(message = "重置密码不能为空")
    private String password;
}
