package com.jeebud.core.data.redis;

import com.jeebud.common.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Component
public class RedisPublishService {
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 发布方法
     *
     * @param channel 消息发布订阅 主题
     * @param message 消息信息
     */
    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, JsonUtils.toJsonString(message));
    }
}
