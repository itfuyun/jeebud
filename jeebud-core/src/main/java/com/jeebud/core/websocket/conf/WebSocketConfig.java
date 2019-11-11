package com.jeebud.core.websocket.conf;

import com.jeebud.common.constant.SysConsts;
import com.jeebud.core.shiro.util.ShiroUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator{
    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        //存入httpsession
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
        //存入当前用户
        sec.getUserProperties().put(SysConsts.WEBSOCKET_SESSION_USER, ShiroUtils.getCurrentUser());
        super.modifyHandshake(sec, request, response);

    }

    /**
     * 自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
