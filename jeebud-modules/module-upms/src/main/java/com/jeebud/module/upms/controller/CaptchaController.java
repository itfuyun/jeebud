package com.jeebud.module.upms.controller;

import com.jeebud.common.constant.ParamConsts;
import com.jeebud.common.constant.SysConsts;
import com.jeebud.common.util.StringUtils;
import com.jeebud.core.captcha.Captcha;
import com.jeebud.core.captcha.CaptchaCodeHelper;
import com.jeebud.common.constant.CaptchaTypeEnum;
import com.jeebud.core.web.RestEntity;
import com.jeebud.core.data.redis.RedisService;
import com.jeebud.module.upms.model.entity.Param;
import com.jeebud.module.upms.service.ParamService;
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
    @Autowired
    ParamService paramService;

    @GetMapping("/image")
    public RestEntity<Captcha> captchaCode() throws IOException {
        int size = 4;
        Param captchaSizeParam = paramService.findByParamKey(ParamConsts.PARAMKEY_CAPTCHA_SIZE);
        String value = captchaSizeParam.getParamValue();
        if(StringUtils.isNumeric(value)){
            size = Integer.valueOf(value);
        }
        Captcha captcha = CaptchaCodeHelper.generateCaptcha(SysConsts.CAPTCHA_WIDTH, SysConsts.CAPTCHA_HEIGHT, size, CaptchaTypeEnum.BASE64_IMAGE);
        redisService.set(CaptchaCodeHelper.KEY_PREFIX + captcha.getCaptchaKey(), captcha.getValue(), 300);
        return RestEntity.ok().data(captcha);
    }

}
