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
public class UpdateUserInfoForm extends Form {
    private Long id;
    private String avatar;
    private String name;
    private String mobile;
    private String email;
    private String profile;
}
