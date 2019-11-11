package com.jeebud.core.websocket.bean;

import com.jeebud.common.constant.UserTypeEnum;
import lombok.Data;

import javax.websocket.Session;

/**
 * <p>Description: 记录连接用户和session映射关系实体</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class WebSocketBean {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户类型
     */
    private UserTypeEnum userType;
    /**
     * http sessionId
     */
    private String sessionId;
    /**
     * websocket session
     */
    private Session session;
}
