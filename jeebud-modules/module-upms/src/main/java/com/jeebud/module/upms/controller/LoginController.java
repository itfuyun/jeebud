package com.jeebud.module.upms.controller;

import com.jeebud.common.constant.ParamConsts;
import com.jeebud.common.constant.SysConsts;
import com.jeebud.common.util.IpUtils;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.common.util.StringUtils;
import com.jeebud.core.captcha.Captcha;
import com.jeebud.core.captcha.CaptchaCodeHelper;
import com.jeebud.common.constant.CaptchaTypeEnum;
import com.jeebud.common.constant.LoginTypeEnum;
import com.jeebud.common.exception.JeebudException;
import com.jeebud.core.captcha.CaptchaException;
import com.jeebud.core.shiro.util.ShiroUtils;
import com.jeebud.core.web.RestEntity;
import com.jeebud.core.data.redis.RedisService;
import com.jeebud.core.log.LogPublisher;
import com.jeebud.core.shiro.JeebudToken;
import com.jeebud.module.upms.model.entity.Param;
import com.jeebud.module.upms.service.ParamService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("${jeebud.sys.serverCtx}")
public class LoginController {
    @Autowired
    LogPublisher logPublisher;
    @Autowired
    RedisService redisService;
    @Autowired
    ParamService paramService;
    @Value("${jeebud.sys.serverCtx}")
    private String serverCtx;

    /**
     * 登陆页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) throws IOException {
        Param captchaOpenParam = paramService.findByParamKey(ParamConsts.PARAMKEY_CAPTCHA_OPEN);
        boolean open = captchaOpenParam.getParamValue().equals("1");
        model.addAttribute("captchaOpen", open);
        if (open) {
            int size = 4;
            Param captchaSizeParam = paramService.findByParamKey(ParamConsts.PARAMKEY_CAPTCHA_SIZE);
            String value = captchaSizeParam.getParamValue();
            if(StringUtils.isNumeric(value)){
                size = Integer.valueOf(value);
            }
            Captcha captcha = CaptchaCodeHelper.generateCaptcha(SysConsts.CAPTCHA_WIDTH, SysConsts.CAPTCHA_HEIGHT, size, CaptchaTypeEnum.BASE64_IMAGE);
            model.addAttribute("captcha", captcha);
            redisService.set(CaptchaCodeHelper.KEY_PREFIX + captcha.getCaptchaKey(), captcha.getValue(), 300);
        }
        return "admin/login" ;
    }

    /**
     * 登陆验证
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/doLogin")
    @ResponseBody
    public RestEntity<String> doLogin(HttpServletRequest request, String username, String password, String captchaKey, String captchaCode) {
        Subject subject = SecurityUtils.getSubject();
        JeebudToken token = new JeebudToken(username, password, captchaKey, captchaCode, LoginTypeEnum.ADMIN_LOGIN);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new JeebudException("账号不存在");
        } catch (IncorrectCredentialsException e) {
            throw new JeebudException("密码不正确");
        } catch (LockedAccountException e) {
            throw new JeebudException("用户被锁定");
        } catch (CaptchaException e) {
            throw new JeebudException("验证码错误");
        } catch (AuthenticationException e) {
            throw new JeebudException("用户验证失败");
        }
        //记录日志
        logPublisher.publishLoginLog(ShiroUtils.getCurrentUser(), IpUtils.getIpAddr(request));
        return RestEntity.ok().data(subject.getSession().getId().toString());
    }

    /**
     * 退出登陆
     *
     * @return
     */
    @GetMapping("/logout")
    public String logOut() {
        SecurityUtils.getSubject().logout();
        return "redirect:" + serverCtx + "/login" ;
    }
}
