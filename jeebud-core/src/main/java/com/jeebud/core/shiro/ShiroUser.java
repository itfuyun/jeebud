package com.jeebud.core.shiro;

import com.jeebud.common.constant.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@AllArgsConstructor
public class ShiroUser implements Serializable {
    private Long id;
    /**
     * 用户类型
     */
    private UserTypeEnum userType;
    /**
     * 名称
     */
    private String name;
    /**
     * 账号
     */
    private String username;



}
