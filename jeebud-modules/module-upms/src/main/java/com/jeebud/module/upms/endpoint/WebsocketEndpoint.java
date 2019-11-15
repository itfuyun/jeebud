package com.jeebud.module.upms.endpoint;

import com.jeebud.common.constant.MsgTypeEnum;
import com.jeebud.common.constant.SysConsts;
import com.jeebud.common.util.SpringUtils;
import com.jeebud.core.data.redis.RedisPublishService;
import com.jeebud.core.shiro.ShiroUser;
import com.jeebud.core.websocket.WebSocketBeanManager;
import com.jeebud.core.websocket.bean.SocketMsg;
import com.jeebud.core.websocket.bean.WebSocketBean;
import com.jeebud.core.websocket.conf.WebSocketConfig;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Component
@ServerEndpoint(value = "/websocket", configurator = WebSocketConfig.class)
@Slf4j
@ToString
public class WebsocketEndpoint {

    /**
     * 因为@ServerEndpoint不支持注入
     */
    private StringRedisTemplate redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
    private RedisPublishService publishService = SpringUtils.getBean(RedisPublishService.class);
    private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils.getBean(RedisMessageListenerContainer.class);

    /**
     * 存放该服务器该ws的所有连接。用处：比如向所有连接该ws的用户发送通知消息。
     */
    private static CopyOnWriteArraySet<WebsocketEndpoint> sessions = new CopyOnWriteArraySet<>();

    /**
     * 存放用户的id与session关系
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.debug("websocket:打开连接");
        HttpSession httpSession = getHttpSession(session);
        ShiroUser user =  getShiroUser(session);
        clients.put(user.getId().toString(), session);
        this.session = session;
        sessions.add(this);
        WebSocketBean webSocketBean = new WebSocketBean();
        webSocketBean.setId(user.getId());
        webSocketBean.setUserType(user.getUserType());
        webSocketBean.setSessionId(httpSession.getId());
        webSocketBean.setSession(session);

        WebSocketBeanManager.getInstance().add(user.getId().toString(),webSocketBean);

    }

    @OnClose
    public void onClose(Session session) {
        ShiroUser user =  getShiroUser(session);
        WebSocketBeanManager.getInstance().remove(user.getId().toString());
        log.debug("websocket:关闭连接");
        sessions.remove(this);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        ShiroUser user =  getShiroUser(session);
        SocketMsg socketMsg = new SocketMsg();
        socketMsg.setTo("0");
        socketMsg.setFrom(user.getName());
        socketMsg.setContent(user.getName()+"："+message);
        socketMsg.setMsgType(MsgTypeEnum.TEXT);
        socketMsg.setScope(0);
        publishService.publish(SysConsts.TOPIC_WEBSOCKET, socketMsg);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        log.debug("websocket:出现错误");
    }

    /**
     * 通过session确认给谁发信息
     *
     * @param session
     * @param message
     */
    public void sendMessage(String message, Session session) throws IOException {
        session.getAsyncRemote().sendText(message);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private ShiroUser getShiroUser(Session session){
       return  (ShiroUser) session.getUserProperties().get(SysConsts.WEBSOCKET_SESSION_USER);
    }

    private HttpSession getHttpSession(Session session){
        return (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
    }
}