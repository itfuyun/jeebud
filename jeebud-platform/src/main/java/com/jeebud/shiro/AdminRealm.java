package com.jeebud.shiro;

import com.jeebud.common.constant.UserTypeEnum;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.common.util.StringUtils;
import com.jeebud.core.captcha.CaptchaCodeHelper;
import com.jeebud.core.captcha.CaptchaException;
import com.jeebud.core.data.redis.RedisService;
import com.jeebud.core.shiro.JeebudToken;
import com.jeebud.core.shiro.ShiroUser;
import com.jeebud.module.upms.model.entity.Permission;
import com.jeebud.module.upms.model.entity.User;
import com.jeebud.module.upms.service.PermissionService;
import com.jeebud.module.upms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    UserService userService;
    @Autowired
    @Lazy
    PermissionService permissionService;
    @Autowired
    @Lazy
    RedisService redisService;
    @Value("${jeebud.captcha.open}")
    boolean open;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = userService.findById(shiroUser.getId());
        List<Permission> permissionList;
        if (user.getAdminFlag() == 1) {
            permissionList = permissionService.getPermissionAll();
        } else {
            permissionList = permissionService.getPermissionByRoleId(user.getRoleId());
        }
        for (Permission p : permissionList) {
            String code = p.getCode();
            if (StringUtils.isNotEmpty(code)) {
                authorizationInfo.addStringPermission(code);
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JeebudToken jeebudToken = (JeebudToken) token;
        if (open && !checkCaptcha(jeebudToken.getCaptchaKey(), jeebudToken.getCaptchaCode())) {
            throw new CaptchaException();
        }
        //获取用户的输入的账号.
        String userName = (String) token.getPrincipal();
        User user = userService.findByUsername(userName);
        if (ObjectUtils.isNull(user)) {
            return null;
        }
        if (user.getStatus() == 1) {
            throw new LockedAccountException("用户被锁定");
        }
        ShiroUser shiroUser = new ShiroUser(user.getId(), UserTypeEnum.ADMIN,user.getName(),user.getUsername());
        return new SimpleAuthenticationInfo(
                shiroUser,
                // 密码
                user.getPassword(),
                // realm name
                getName()
        );
    }

    /**
     * 校验验证码
     *
     * @param captchaKey
     * @param captchaCode
     * @return
     */
    private boolean checkCaptcha(String captchaKey, String captchaCode) {
        if (StringUtils.isAnyEmpty(captchaKey, captchaCode)) {
            return false;
        }
        Object value = redisService.get(CaptchaCodeHelper.KEY_PREFIX + captchaKey);
        if (ObjectUtils.isNull(value)) {
            return false;
        }
        clearCaptcha(captchaKey);
        return value.toString().toLowerCase().equals(captchaCode.toLowerCase());
    }

    /**
     * 清除验证码
     *
     * @param captchaKey
     */
    private void clearCaptcha(String captchaKey) {
        redisService.del(CaptchaCodeHelper.KEY_PREFIX + captchaKey);
    }
}
