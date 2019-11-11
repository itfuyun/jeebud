package com.jeebud.core.websocket;

import com.jeebud.common.util.ObjectUtils;
import com.jeebud.core.websocket.bean.WebSocketBean;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 管理WebSocketBean 监听redis消息，遍历管理者实现分布式websocket</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public class WebSocketBeanManager {
    private final ConcurrentHashMap<String, WebSocketBean> cacheMap = new ConcurrentHashMap<>();

    private WebSocketBeanManager(){}

    private static WebSocketBeanManager instance = new WebSocketBeanManager();

    public static WebSocketBeanManager getInstance(){
        return instance;
    }

    public void add(String key, WebSocketBean session){
        if(ObjectUtils.isNull(session)){
            cacheMap.remove(key);
        }else {
            cacheMap.put(key,session);
        }
    }

    public void remove(String key){
        cacheMap.remove(key);
    }

    public WebSocketBean get(String key){
       return cacheMap.get(key);
    }

    public Map<String, WebSocketBean> getAll(){
        return cacheMap;
    }

    public int size(){
        return cacheMap.size();
    }
}
