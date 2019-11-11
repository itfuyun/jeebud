package com.jeebud.core.websocket.bean;

import com.jeebud.common.constant.MsgTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: websocket 消息实体</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class SocketMsg implements Serializable{
    /**
     * 接收人
     */
    private String to;
    /**
     * 发送人
     */
    private String from;
    /**
     * 发送范围
     */
    private Integer scope;
    /**
     * 消息类型
     */
    private MsgTypeEnum msgType;
    /**
     * 消息内容
     */
    private String content;
}
