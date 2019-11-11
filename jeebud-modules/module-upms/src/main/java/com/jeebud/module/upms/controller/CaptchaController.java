package com.jeebud.module.upms.controller;

import com.jeebud.core.captcha.Captcha;
import com.jeebud.core.captcha.CaptchaCodeHelper;
import com.jeebud.common.constant.CaptchaTypeEnum;
import com.jeebud.core.web.RestEntity;
import com.jeebud.core.data.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@RestController
@RequestMapping("${jeebud.sys.serverCtx}/captcha")
public class CaptchaController {
    @Autowired
    RedisService redisService;

    @Value("${jeebud.captcha.width}")
    int width;
    @Value("${jeebud.captcha.height}")
    int height;
    @Value("${jeebud.captcha.size}")
    int size;

    @GetMapping("/image")
    public RestEntity<Captcha> captchaCode() throws IOException {
        Captcha captcha = CaptchaCodeHelper.generateCaptcha(width, height, size, CaptchaTypeEnum.BASE64_IMAGE);
        redisService.set(CaptchaCodeHelper.KEY_PREFIX + captcha.getCaptchaKey(), captcha.getValue(), 300);
        return RestEntity.ok().data(captcha);
    }

}
