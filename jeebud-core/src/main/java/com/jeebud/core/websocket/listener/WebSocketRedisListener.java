package com.jeebud.core.websocket.listener;

import com.jeebud.common.util.JsonUtils;
import com.jeebud.core.websocket.WebSocketBeanManager;
import com.jeebud.core.websocket.bean.SocketMsg;
import com.jeebud.core.websocket.bean.WebSocketBean;
import lombok.Data;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.websocket.Session;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class WebSocketRedisListener implements MessageListener {

    /**
     * 订阅接收发布者的消息
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        System.out.println(new String(pattern) + "主题发布：" + msg);
        SocketMsg socketMsg = null;
        try {
            socketMsg = JsonUtils.toJsonObject(msg, SocketMsg.class);
        } catch (Exception e) {
        }
        WebSocketBeanManager webSocketBeanManager = WebSocketBeanManager.getInstance();
        Map<String, WebSocketBean> map =webSocketBeanManager.getAll();
        for(Map.Entry<String, WebSocketBean> entry:map.entrySet()){
            send(entry.getValue(),socketMsg);
        }

    }

    private void send(WebSocketBean webSocketBean, SocketMsg socketMsg){
        if (null != socketMsg && null != webSocketBean) {
            Integer scope = socketMsg.getScope();
            if (scope == 0 || webSocketBean.getUserType().getType().equals(scope)) {
                //不限类型或者类型匹配
                Session session = webSocketBean.getSession();
                if (null != session && session.isOpen()) {
                    if ("0".equals(socketMsg.getTo()) || socketMsg.getTo().equals(webSocketBean.getId().toString())) {
                        session.getAsyncRemote().sendText(socketMsg.getContent());
                    }
                }
            }
        }
    }
}